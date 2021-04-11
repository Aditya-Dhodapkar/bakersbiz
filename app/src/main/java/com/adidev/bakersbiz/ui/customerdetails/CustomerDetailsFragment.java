package com.adidev.bakersbiz.ui.customerdetails;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.adidev.bakersbiz.GlobalClass;
import com.adidev.bakersbiz.R;
import com.adidev.bakersbiz.UIUtils;
import com.adidev.bakersbiz.model.Customer;
import com.adidev.bakersbiz.ui.customers.CustomerFragmentDirections;
import com.adidev.bakersbiz.ui.orderdetails.OrderDetailsFragmentDirections;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import java.io.IOException;
import java.io.InputStream;

public class CustomerDetailsFragment extends Fragment {

    private CustomerDetailsViewModel customerDetailsViewModel;
    private Customer customer;
    private CustomerDetailsFragmentArgs args;
    private View root;
    private Button updateButton;

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
        root = inflater.inflate(R.layout.fragment_customer_details, container, false);

        customerDetailsViewModel = new CustomerDetailsViewModel(((GlobalClass) applicationContext).getRepository(), customer);

        View personPhoto = root.findViewById(R.id.person_photo);
        RetrieveContactPhoto((ImageView) personPhoto, customer.getContactID() );

        TextView phoneView = root.findViewById(R.id.person_phone);
        phoneView.setText(UIUtils.getFormattedNumber(customer.getPhone()));

        TextView notesView = root.findViewById(R.id.customer_notes);
        notesView.setText(customer.getNotes());

        final TextView textView = root.findViewById(R.id.person_name);
        customerDetailsViewModel.getCustomer().observe(getViewLifecycleOwner(), new Observer<Customer>() {
            @Override
            public void onChanged(@Nullable Customer customer) {
                textView.setText(customer.getName());
            }
        });

        updateButton = root.findViewById(R.id.update_customer_details);
        final Fragment thisFragment = this;

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView notesView = root.findViewById(R.id.customer_notes);
                final CharSequence text = notesView.getText();
                customer.setCustomerNotes(text.toString());
                ((GlobalClass) getContext().getApplicationContext()).getRepository().updateCustomer(customer);

                NavDirections directions = CustomerDetailsFragmentDirections.navigateToDashboard();
                NavHostFragment.findNavController(thisFragment).navigate(directions);
            }
        });

        return root;
    }

    public void RetrieveContactPhoto(ImageView personPhoto, Long contactID) {

        Bitmap photo = null;
        try {
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(personPhoto.getContext().getContentResolver(),
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        TextView notesView = root.findViewById(R.id.customer_notes);
        final CharSequence text = notesView.getText();
        customer.setCustomerNotes(text.toString());
        ((GlobalClass) getContext().getApplicationContext()).getRepository().updateCustomer(customer);
    }
}