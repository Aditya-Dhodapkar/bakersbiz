package com.adidev.bakersbiz.ui.customers;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.adidev.bakersbiz.R;
import com.adidev.bakersbiz.model.Customer;
import com.adidev.bakersbiz.repository.Repository;

import static androidx.recyclerview.widget.RecyclerView.*;

public class CustomerDataAdapter extends Adapter {

    Repository repository;
    Fragment associatedFragment;
    public CustomerDataAdapter(Object dataSet, Fragment associatedFragment) {
        repository = (Repository) dataSet;
        this.associatedFragment = associatedFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new CustomerViewHolder(v, associatedFragment);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Customer customer = repository.getCustomers().get(position);
        ((CustomerViewHolder)holder).personName.setText(customer.getName());
        ((CustomerViewHolder)holder).personPhone.setText(customer.getPhone());
        Bitmap image = null;
        image = ((CustomerViewHolder)holder).RetrieveContactPhoto(customer.getContactID());
        if(image != null)
            ((CustomerViewHolder)holder).personPhoto.setImageBitmap(image);

        ((CustomerViewHolder)holder).setCustomer(customer);
    }
    @Override
    public int getItemCount() {
        return repository.getCustomers().size();
    }

}
