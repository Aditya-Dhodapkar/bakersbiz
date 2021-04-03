package com.adidev.bakersbiz.ui.orderdetails;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adidev.bakersbiz.model.Customer;
import com.adidev.bakersbiz.model.Order;
import com.adidev.bakersbiz.repository.Repository;

public class OrderDetailsViewModel extends ViewModel {

    private MutableLiveData<Order> orderData;
    private Repository repository;

    public OrderDetailsViewModel(Repository repository, Order order) {
        this.repository = repository;
        orderData = new MutableLiveData<>(order);
    }

    public LiveData<Order> getOrder() {
        return orderData;
    }
}
