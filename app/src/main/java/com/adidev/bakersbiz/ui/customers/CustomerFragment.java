package com.adidev.bakersbiz.ui.customers;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adidev.bakersbiz.GlobalClass;
import com.adidev.bakersbiz.MainActivity;
import com.adidev.bakersbiz.R;
import com.adidev.bakersbiz.model.Customer;
import com.adidev.bakersbiz.ui.Orders.OrdersFragmentDirections;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.app.Activity.RESULT_OK;

public class CustomerFragment extends Fragment {

    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    private CustomerViewModel customerViewModel;
    private RecyclerView customersRecyclerView;
    private RecyclerView.LayoutManager customersLayoutManager;
    private Uri uriContact;
    private String contactID;     // contacts unique ID
    private String contactName;
    private String contactNumber;
    private Customer longClickedCustomer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        customerViewModel = new CustomerViewModel(((GlobalClass)getContext().getApplicationContext()).getRepository(), this);

        //final TextView textView = root.findViewById(R.id.text_dashboard);
        customerViewModel.getCustomerData().observe(getViewLifecycleOwner(), new Observer<RecyclerView.Adapter>() {
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
        customersRecyclerView.setAdapter(customerViewModel.getCustomerData().getValue());

        //https://gist.github.com/evandrix/7058235

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission(Manifest.permission.READ_CONTACTS, 100 );
                startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
            }
        });

        registerForContextMenu(customersRecyclerView);

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {
            uriContact = data.getData();
            retrieveContactName();
            retrieveContactNumber();
            customerViewModel.AddCustomer(new Long(contactID), contactName, contactNumber);
        }
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.customerviewcontextmenu, menu);
        menu.setHeaderTitle("Select Action");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.call:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+ longClickedCustomer.getPhone()));
                try {
                    checkPermission(Manifest.permission.CALL_PHONE, 100 );
                    startActivity(callIntent);
                }
                catch(Exception exp) {
                    exp.printStackTrace();
                }
                return true;
            case R.id.delete:
                customerViewModel.DeleteCustomer(longClickedCustomer);
                return true;
            case R.id.orders:
                CustomerFragmentDirections.NavigateToHome directions = CustomerFragmentDirections.navigateToHome(longClickedCustomer.getName());
                NavHostFragment.findNavController(this).navigate(directions);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void setLongClickedCustomer(Customer customer) {
        longClickedCustomer = customer;
    }

    private void checkPermission(String permission, int requestCode)
    {
        // Checking if permission is not granted
        if (ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat .requestPermissions(getActivity(), new String[] { permission }, requestCode);
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