package com.adidev.bakersbiz.ui.dashboard;

import android.content.ContentUris;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.adidev.bakersbiz.R;
import com.adidev.bakersbiz.model.Customer;
import com.adidev.bakersbiz.repository.Repository;

import java.io.IOException;
import java.io.InputStream;

import static androidx.recyclerview.widget.RecyclerView.*;

public class CustomerDataAdapter extends Adapter {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class CustomerViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public View card;
        public  TextView personName;
        public  TextView personPhone;
        public  ImageView personPhoto;

        public CustomerViewHolder(View v) {
            super(v);
            card = (CardView)v.findViewById(R.id.cv);
            personName = (TextView)v.findViewById(R.id.person_name);
            personPhone = (TextView)v.findViewById(R.id.person_phone);
            personPhoto = (ImageView)v.findViewById(R.id.person_photo);
        }

        public void RetrieveContactPhoto(Long contactID) {

            Bitmap photo = null;
            try {
                InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(card.getContext().getContentResolver(),
                        ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactID));

                if (inputStream != null) {
                    photo = BitmapFactory.decodeStream(inputStream);
                    personPhoto.setImageBitmap(photo);
                    assert inputStream != null;
                    inputStream.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    Repository repository;
    public CustomerDataAdapter(Object dataSet) {
        repository = (Repository) dataSet;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new CustomerViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Customer customer = repository.getCustomers().get(position);
        ((CustomerViewHolder)holder).personName.setText(customer.getName());
        ((CustomerViewHolder)holder).personPhone.setText(customer.getPhone());
        ((CustomerViewHolder)holder).RetrieveContactPhoto(customer.getContactID());
    }

    @Override
    public int getItemCount() {
        return repository.getCustomers().size();
    }

}
