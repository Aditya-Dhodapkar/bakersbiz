package com.adidev.bakersbiz.ui.Orders;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.adidev.bakersbiz.R;
import com.adidev.bakersbiz.model.Customer;
import com.adidev.bakersbiz.model.MenuItem;
import com.adidev.bakersbiz.model.Order;
import com.adidev.bakersbiz.repository.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static androidx.recyclerview.widget.RecyclerView.*;

public class OrdersDataAdapter extends RecyclerView.Adapter{

    Repository repository;
    Fragment associatedFragment;
    String customerName;
    List<Order> orders;
    public OrdersDataAdapter(Object dataSet, Fragment associatedFragment, String customerName) {
        repository = (Repository) dataSet;
        this.associatedFragment = associatedFragment;
        this.customerName = customerName;
        orders = repository.getOrders();
        List<Order> tempOrders = new ArrayList<Order>();
        if(customerName != null && !customerName.isEmpty()) {
            for (int i = 0; i < orders.size(); i++) {
                if (orders.get(i).getCustomerName().compareToIgnoreCase(customerName) == 0)
                    tempOrders.add(orders.get(i));
            }
            orders = tempOrders;
        }
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
        Order order = orders.get(position);

        Date curDate = java.util.Calendar.getInstance().getTime();
        if(order.getOrderDeliveryDate().compareTo(curDate) > 0) {
            ((OrdersViewHolder) holder).orderItem.setTextColor(0xFF00FF00);
        }

        DateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, ''yyyy");
        String strDate = dateFormat.format(order.getOrderDeliveryDate());
        ((OrdersViewHolder)holder).orderDeliveryDate.setText(strDate);
        ((OrdersViewHolder)holder).orderPrice.setText("₹ " + String.valueOf(order.getPrice()));
        ((OrdersViewHolder)holder).orderItem.setText(order.getItemName());

        String unitCount;
        if(order.getNumberOfItems() == 1)
            unitCount = String.valueOf(order.getNumberOfItems()) + "unit ";
        else
            unitCount = String.valueOf(order.getNumberOfItems()) + "units ";

        ((OrdersViewHolder)holder).orderQuantity.setText(unitCount);
        ((OrdersViewHolder)holder).orderCustomerName.setText(order.getCustomerName());
        ((OrdersViewHolder)holder).setOrder(order);
    }
    @Override
    public int getItemCount() {
        return orders.size();
    }
}