package com.adidev.bakersbiz.ui.customerdetails;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.adidev.bakersbiz.model.Customer;
import com.adidev.bakersbiz.repository.Repository;
import com.adidev.bakersbiz.ui.dashboard.CustomerDataAdapter;

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
