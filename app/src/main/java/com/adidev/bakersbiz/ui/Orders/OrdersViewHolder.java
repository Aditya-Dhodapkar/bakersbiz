package com.adidev.bakersbiz.ui.Orders;
import android.content.ContentUris;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.adidev.bakersbiz.R;
import com.adidev.bakersbiz.model.Customer;
import com.adidev.bakersbiz.model.MenuItem;
import com.adidev.bakersbiz.model.Order;
import com.adidev.bakersbiz.ui.menudetails.MenuDetailsFragment;

import java.io.IOException;
import java.io.InputStream;

public class OrdersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener , View.OnLongClickListener{
    private Order order;
    private Fragment associatedFragment;
    // each data item is just a string in this case
    public View card;
    public TextView orderDeliveryDate;
    public  TextView orderPrice;
    public TextView orderItem;
    public  TextView orderQuantity;
    public TextView orderCustomerName;
    public ImageView itemImagePhoto;

    public OrdersViewHolder(View v, Fragment associatedFragment) {
        super(v);
        card = v.findViewById(R.id.cv);
        orderDeliveryDate = (TextView)v.findViewById(R.id.order_delivery_date);
        orderPrice = (TextView)v.findViewById(R.id.order_price);
        orderItem = (TextView)v.findViewById(R.id.order_item);
        orderQuantity = (TextView)v.findViewById(R.id.order_item_quantity);
        orderCustomerName = (TextView)v.findViewById(R.id.order_item_customer);
        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
        this.associatedFragment = associatedFragment;
    }

    public void setOrder(Order order){
        this.order = order;
    }

    @Override
    public void onClick(View view) {

        OrdersFragmentDirections.NavigateToOrderDetail directions =
                OrdersFragmentDirections.navigateToOrderDetail(order.getOrderID());
        NavHostFragment.findNavController(associatedFragment).navigate(directions);
    }

    @Override
    public boolean onLongClick(View view) {
        ((OrdersFragment)associatedFragment).setLongClickedOrder(order);
        return false;
    }
}

