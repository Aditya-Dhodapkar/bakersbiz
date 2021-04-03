package com.adidev.bakersbiz.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.adidev.bakersbiz.model.Customer;
import com.adidev.bakersbiz.model.MenuItem;
import com.adidev.bakersbiz.ui.customers.CustomerViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MenuFragment extends Fragment {

    private MenuViewModel menuViewModel;
    private RecyclerView menuRecyclerView;
    private RecyclerView.LayoutManager menuLayoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        menuViewModel = new MenuViewModel(((GlobalClass)getContext().getApplicationContext()).getRepository(), this);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        menuViewModel.getMenuData().observe(getViewLifecycleOwner(), new Observer<RecyclerView.Adapter>() {
            @Override
            public void onChanged(@Nullable RecyclerView.Adapter adapter) {
                menuRecyclerView.setAdapter(adapter);
            }
        });

        menuRecyclerView = (RecyclerView) root.findViewById(R.id.orders_list_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        menuRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        menuLayoutManager = new LinearLayoutManager(getContext());

        menuRecyclerView.setLayoutManager(menuLayoutManager);

        // specify an adapter (see also next example)
        menuRecyclerView.setAdapter(menuViewModel.getMenuData().getValue());

        //https://gist.github.com/evandrix/7058235

        FloatingActionButton fab = root.findViewById(R.id.fab_menu);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
            }
        });
        return root;
    }

    public void setLongClickedMenuItem(MenuItem item) {
    }
}