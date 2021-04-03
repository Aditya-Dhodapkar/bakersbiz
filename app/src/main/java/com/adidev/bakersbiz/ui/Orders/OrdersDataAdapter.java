package com.adidev.bakersbiz.ui.Orders;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.adidev.bakersbiz.R;
import com.adidev.bakersbiz.model.Customer;
import com.adidev.bakersbiz.model.MenuItem;
import com.adidev.bakersbiz.model.Order;
import com.adidev.bakersbiz.repository.Repository;

import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.*;

public class OrdersDataAdapter extends RecyclerView.Adapter{

    Repository repository;
    Fragment associatedFragment;
    public OrdersDataAdapter(Object dataSet, Fragment associatedFragment) {
        repository = (Repository) dataSet;
        this.associatedFragment = associatedFragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        RecyclerView.ViewHolder vh = new OrdersViewHolder(v, associatedFragment);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        List<Order> orders = repository.getOrders();
        Order order = orders.get(position);
        ((OrdersViewHolder)holder).orderDeliveryDate.setText(order.getOrderDeliveryDate().toString());
        ((OrdersViewHolder)holder).orderPrice.setText(order.getPrice());
        ((OrdersViewHolder)holder).orderItem.setText(order.getItem().getName());
        ((OrdersViewHolder)holder).orderQuantity.setText(order.getNumberOfItems());
        ((OrdersViewHolder)holder).orderCustomerName.setText(order.getCustomerName());
        ((OrdersViewHolder)holder).setOrder(order);
    }
    @Override
    public int getItemCount() {
        return repository.getOrders().size();
    }
}