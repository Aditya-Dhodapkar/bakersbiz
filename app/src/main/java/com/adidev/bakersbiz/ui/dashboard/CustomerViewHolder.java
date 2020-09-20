package com.adidev.bakersbiz.ui.dashboard;
import android.content.ContentUris;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.adidev.bakersbiz.R;
import com.adidev.bakersbiz.model.Customer;
import com.adidev.bakersbiz.ui.customerdetails.CustomerDetailsFragment;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;

import java.io.IOException;
import java.io.InputStream;

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public class CustomerViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener , View.OnLongClickListener{
    private Customer customer;
    private Fragment associatedFragment;
    // each data item is just a string in this case
    public View card;
    public TextView personName;
    public  TextView personPhone;
    public  ImageView personPhoto;

    public CustomerViewHolder(View v, Fragment associatedFragment) {
        super(v);
        card = v.findViewById(R.id.cv);
        personName = (TextView)v.findViewById(R.id.person_name);
        personPhone = (TextView)v.findViewById(R.id.person_phone);
        personPhoto = (ImageView)v.findViewById(R.id.person_photo);
        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
        this.associatedFragment = associatedFragment;
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

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    @Override
    public void onClick(View view) {

       DashboardFragmentDirections.NavigateToCustomerDetail directions =
            DashboardFragmentDirections.navigateToCustomerDetail(customer.getCustomerID());
        NavHostFragment.findNavController(associatedFragment).navigate(directions);
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}
