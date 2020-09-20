package com.adidev.bakersbiz.ui.customerdetails;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.adidev.bakersbiz.GlobalClass;
import com.adidev.bakersbiz.R;
import com.adidev.bakersbiz.model.Customer;
import com.adidev.bakersbiz.ui.dashboard.DashboardViewModel;
import com.adidev.bakersbiz.ui.home.HomeViewModel;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class CustomerDetailsFragment extends Fragment {

    private CustomerDetailsViewModel customerDetailsViewModel;
    private Customer customer;
    private CustomerDetailsFragmentArgs args;

    public CustomerDetailsFragment(){ }
    public CustomerDetailsFragment(Customer customer) {
        this.customer = customer;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = CustomerDetailsFragmentArgs.fromBundle(requireArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Context context = getContext();
        Context applicationContext = context.getApplicationContext();

        customer = ((GlobalClass) applicationContext).getRepository().getCustomer(args.getCustomerId());
        View root = inflater.inflate(R.layout.fragment_customer_details, container, false);
        customerDetailsViewModel = new CustomerDetailsViewModel(((GlobalClass) applicationContext).getRepository(), customer);

        final TextView textView = root.findViewById(R.id.person_name);
        customerDetailsViewModel.getCustomer().observe(getViewLifecycleOwner(), new Observer<Customer>() {
            @Override
            public void onChanged(@Nullable Customer customer) {
                textView.setText(customer.getName());
            }
        });

        return root;
    }
}