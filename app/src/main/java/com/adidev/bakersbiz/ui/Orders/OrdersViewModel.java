package com.adidev.bakersbiz.ui.Orders;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.adidev.bakersbiz.model.Customer;
import com.adidev.bakersbiz.model.MenuItem;
import com.adidev.bakersbiz.model.Order;
import com.adidev.bakersbiz.repository.Repository;
import com.adidev.bakersbiz.ui.customers.CustomerDataAdapter;

import java.time.LocalDateTime;
import java.util.Date;

public class OrdersViewModel extends ViewModel {

    private MutableLiveData<RecyclerView.Adapter> orderData;
    private Repository repo;
    private Fragment associatedFragment;


    public LiveData<RecyclerView.Adapter> getOrderData() {return orderData;}

    public OrdersViewModel(Repository repo, Fragment associatedFragment, String customerName) {
        this.repo = repo;
        this.associatedFragment = associatedFragment;
        RecyclerView.Adapter orderAdapter = new OrdersDataAdapter(repo, associatedFragment, customerName);
        orderData = new MutableLiveData<>();
        orderData.setValue(orderAdapter);
    }

    //This method updates the repository and then tells the List Adapter that the data had changed.
    public void AddOrder(int orderID, int customerID, int price, String customerName, MenuItem item, int discounts, int numberOfItems, Date orderDeliveryDate) {
        Order order = new Order(orderID, customerID,  price, customerName,  item.getName(),  discounts,  numberOfItems,  orderDeliveryDate);
        repo.addNewOrder(order);
        //This should update the list UI to refresh itself if the underlying data in the store changed.
        orderData.getValue().notifyDataSetChanged();
    }

    public void UpdateOrder(Order order) {
        repo.updateOrder(order);
    }

    public void DeleteOrder(Order order) {
        repo.deleteOrder(order);
        //This should update the list UI to refresh itself if the underlying data in the store changed.
        orderData.getValue().notifyDataSetChanged();
    }
}