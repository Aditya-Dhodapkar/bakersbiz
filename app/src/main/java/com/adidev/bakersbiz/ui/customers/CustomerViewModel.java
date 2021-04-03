package com.adidev.bakersbiz.ui.customers;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.adidev.bakersbiz.model.Customer;
import com.adidev.bakersbiz.repository.Repository;
import com.adidev.bakersbiz.ui.customerdetails.CustomerDetailsFragment;

public class CustomerViewModel extends ViewModel {

    private MutableLiveData<RecyclerView.Adapter> customerData;
    private Repository repository;
    CustomerDetailsFragment detailsFragment;

    public CustomerViewModel(Repository repo, Fragment associatedFragment) {
        repository = repo;
        RecyclerView.Adapter customersAdapter = new CustomerDataAdapter(repository, associatedFragment);
        customerData = new MutableLiveData<>();
        customerData.setValue(customersAdapter);
    }

    public LiveData<RecyclerView.Adapter> getCustomerData() {return customerData;}


    //This method updates the repository and then tells the List Adapter that the data had changed.
    public void AddCustomer(Long contactID, String contactName, String contactNumber) {
        Customer customer = new Customer(contactID, contactName, contactNumber);
        repository.addNewCustomer(customer);
        //This should update the list UI to refresh itself if the underlying data in the store changed.
        customerData.getValue().notifyDataSetChanged();
    }

    public void UpdateCustomer(Customer customer) {
        repository.updateCustomer(customer);
    }

    public void DeleteCustomer(Customer customer) {
        repository.DeleteCustomer(customer);
        //This should update the list UI to refresh itself if the underlying data in the store changed.
        customerData.getValue().notifyDataSetChanged();
    }
}