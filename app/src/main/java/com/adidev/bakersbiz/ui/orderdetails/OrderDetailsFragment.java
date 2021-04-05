package com.adidev.bakersbiz.ui.orderdetails;
import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavHost;
import androidx.navigation.fragment.NavHostFragment;

import com.adidev.bakersbiz.GlobalClass;
import com.adidev.bakersbiz.R;
import com.adidev.bakersbiz.UIUtils;
import com.adidev.bakersbiz.model.Customer;
import com.adidev.bakersbiz.model.Order;
import com.adidev.bakersbiz.ui.Orders.OrdersFragmentDirections;
import com.adidev.bakersbiz.ui.customerdetails.CustomerDetailsFragmentArgs;
import com.adidev.bakersbiz.ui.customerdetails.CustomerDetailsViewModel;
import com.adidev.bakersbiz.ui.customers.CustomerFragmentDirections;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderDetailsFragment extends Fragment {
    private OrderDetailsViewModel orderDetailsViewModel;
    private Order order;
    private OrderDetailsFragmentArgs args;
    private View root;
    EditText customerName;
    EditText deliveryDate;
    EditText priceDetails;
    EditText itemName;
    EditText quantity;
    MultiAutoCompleteTextView orderDescription;
    Button submitOrder;

    public OrderDetailsFragment(){ }
    public OrderDetailsFragment(Order order) {
        this.order = order;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = OrderDetailsFragmentArgs.fromBundle(requireArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Context context = getContext();
        Context applicationContext = context.getApplicationContext();

        order = ((GlobalClass) applicationContext).getRepository().getOrder(args.getOrderId());
        root = inflater.inflate(R.layout.order_details_fragement, container, false);
        orderDetailsViewModel = new OrderDetailsViewModel(((GlobalClass) applicationContext).getRepository(), order);

        final Fragment thisFragment = (Fragment)this;
        customerName = root.findViewById(R.id.order_customer_details);
        deliveryDate = root.findViewById(R.id.order_delivery_date_details);
        priceDetails = root.findViewById(R.id.order_price_details);
        itemName = root.findViewById(R.id.order_item_details);
        quantity = root.findViewById(R.id.order_item_quantity_details);
        orderDescription = root.findViewById(R.id.order_item_description_details);
        submitOrder = root.findViewById(R.id.order_submit_details);
        submitOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                final CharSequence descriptionText = orderDescription.getText();
                final CharSequence deliveryDateText = deliveryDate.getText();
                final CharSequence priceDetailsText = priceDetails.getText();
                final CharSequence itemNameText = itemName.getText();
                final CharSequence quantityText = quantity.getText();
                final CharSequence customerNameText = customerName.getText();

                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    date = format.parse(deliveryDateText.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if(order != null) {
                    Order updatedOrder = new Order(order.getOrderID(),-1,Integer.parseInt(priceDetailsText.toString()),customerNameText.toString()
                            ,itemNameText.toString(), 0, Integer.parseInt(quantityText.toString()),date);
                    ((GlobalClass) getContext().getApplicationContext()).getRepository().updateOrder(updatedOrder);
                }
                else{
                    Order newOrder = new Order(-1,-1,Integer.parseInt(priceDetailsText.toString()),customerNameText.toString()
                            ,itemNameText.toString(), 0, Integer.parseInt(quantityText.toString()),date);
                    ((GlobalClass) getContext().getApplicationContext()).getRepository().addNewOrder(newOrder);
                }

                OrderDetailsFragmentDirections.NavigateToHome directions = OrderDetailsFragmentDirections.navigateToHome("");
                NavHostFragment.findNavController(thisFragment).navigate(directions);

            }
        });

        if(order != null) {
            customerName.setText(order.getCustomerName());
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            deliveryDate.setText(df.format(order.getOrderDeliveryDate()));
            priceDetails.setText(String.valueOf(order.getPrice()));
            itemName.setText(order.getItemName());
            quantity.setText(String.valueOf(order.getNumberOfItems()));
            orderDescription.setText(order.getOrderDescription());
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
