package com.adidev.bakersbiz.ui.customerdetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adidev.bakersbiz.model.Customer;
import com.adidev.bakersbiz.repository.Repository;

public class CustomerDetailsViewModel extends ViewModel {

    private MutableLiveData<Customer> customerData;
    private Repository repository;

    public CustomerDetailsViewModel(Repository repo) {
        repository = repo;
        customerData = new MutableLiveData<>();
        //customerData.setValue();
    }

    public CustomerDetailsViewModel(Repository repository, Customer customer) {
        this.repository = repository;
        customerData = new MutableLiveData<>(customer);
    }

    public LiveData<Customer> getCustomer() {
        return customerData;
    }
}
