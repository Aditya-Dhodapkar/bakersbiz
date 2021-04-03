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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.adidev.bakersbiz.GlobalClass;
import com.adidev.bakersbiz.R;
import com.adidev.bakersbiz.UIUtils;
import com.adidev.bakersbiz.model.Customer;
import com.adidev.bakersbiz.model.Order;
import com.adidev.bakersbiz.ui.customerdetails.CustomerDetailsFragmentArgs;
import com.adidev.bakersbiz.ui.customerdetails.CustomerDetailsViewModel;

import java.io.IOException;
import java.io.InputStream;

public class OrderDetailsFragment extends Fragment {
    private OrderDetailsViewModel orderDetailsViewModel;
    private Order order;
    private OrderDetailsFragmentArgs args;
    private View root;

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

        if(order != null) {

            EditText deliveryDate = root.findViewById(R.id.order_delivery_date_details);
            deliveryDate.setText(order.getOrderDeliveryDate().toString());

            EditText priceDetails = root.findViewById(R.id.order_price_details);
            priceDetails.setText(order.getPrice());

            EditText itemName = root.findViewById(R.id.order_item_details);
            itemName.setText(order.getItem().getName());

            EditText quantity = root.findViewById(R.id.order_item_quantity_details);
            quantity.setText(order.getNumberOfItems());

            MultiAutoCompleteTextView orderDescription = root.findViewById(R.id.order_item_description_details);
            orderDescription.setText(order.getOrderDescription());
        }

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        TextView notesView = root.findViewById(R.id.order_item_description_details);
        final CharSequence text = notesView.getText();
        if(order != null) {
            order.setOrderDescription(text.toString());
            ((GlobalClass) getContext().getApplicationContext()).getRepository().updateOrder(order);
        }
    }
}
