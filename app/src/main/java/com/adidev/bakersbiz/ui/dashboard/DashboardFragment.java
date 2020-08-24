package com.adidev.bakersbiz.ui.dashboard;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adidev.bakersbiz.GlobalClass;
import com.adidev.bakersbiz.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class DashboardFragment extends Fragment {

    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    private DashboardViewModel dashboardViewModel;
    private RecyclerView customersRecyclerView;
    private RecyclerView.LayoutManager customersLayoutManager;
    private Uri uriContact;
    private String contactID;     // contacts unique ID
    private String contactName;
    private String contactNumber;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = new DashboardViewModel(((GlobalClass)getContext().getApplicationContext()).getRepository());

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        //final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getCustomerData().observe(getViewLifecycleOwner(), new Observer<RecyclerView.Adapter>() {
            @Override
            public void onChanged(@Nullable RecyclerView.Adapter adapter) {
                customersRecyclerView.setAdapter(adapter);
            }
        });

        customersRecyclerView = (RecyclerView) root.findViewById(R.id.customer_list_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        customersRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        customersLayoutManager = new LinearLayoutManager(getContext());

        customersRecyclerView.setLayoutManager(customersLayoutManager);

        // specify an adapter (see also next example)
        customersRecyclerView.setAdapter(dashboardViewModel.getCustomerData().getValue());

        //https://gist.github.com/evandrix/7058235

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {
            uriContact = data.getData();
            retrieveContactName();
            retrieveContactNumber();
            dashboardViewModel.AddCustomer(new Long(contactID), contactName, contactName);
        }
    }

    private void retrieveContactNumber() {

        // getting contacts ID
        Cursor cursorID = getContext().getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        // Using the contact ID now we will get contact phone number
        Cursor cursorPhone = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        cursorPhone.close();
    }

    private void retrieveContactName() {
        // querying contact data store

        Cursor cursor = getContext().getContentResolver().query(uriContact, null, null, null, null);

        if (cursor.moveToFirst()) {

            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }

        cursor.close();
    }
}