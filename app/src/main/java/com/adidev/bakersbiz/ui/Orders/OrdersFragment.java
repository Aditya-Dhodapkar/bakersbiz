package com.adidev.bakersbiz.ui.Orders;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adidev.bakersbiz.GlobalClass;
import com.adidev.bakersbiz.R;
import com.adidev.bakersbiz.model.Order;
import com.adidev.bakersbiz.repository.Repository;
import com.adidev.bakersbiz.ui.menu.MenuViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class OrdersFragment extends Fragment {

    private OrdersViewModel ordersViewModel;
    private RecyclerView ordersRecyclerView;
    private RecyclerView.LayoutManager ordersLayoutManager;
    private Order longClickedOrder;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ordersViewModel = new OrdersViewModel(((GlobalClass)getContext().getApplicationContext()).getRepository(), this);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ordersViewModel.getOrderData().observe(getViewLifecycleOwner(), new Observer<RecyclerView.Adapter>() {
            @Override
            public void onChanged(@Nullable RecyclerView.Adapter adapter) {
                ordersRecyclerView.setAdapter(adapter);
            }
        });

        ordersRecyclerView = (RecyclerView) root.findViewById(R.id.orders_list_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        ordersRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        ordersLayoutManager = new LinearLayoutManager(getContext());

        ordersRecyclerView.setLayoutManager(ordersLayoutManager);

        // specify an adapter (see also next example)
        ordersRecyclerView.setAdapter(ordersViewModel.getOrderData().getValue());

        //https://gist.github.com/evandrix/7058235

        final Fragment fragment = this;
        FloatingActionButton fab = root.findViewById(R.id.fab_orders);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrdersFragmentDirections.NavigateToOrderDetail directions =
                        OrdersFragmentDirections.navigateToOrderDetail(-1);
                NavHostFragment.findNavController(fragment).navigate(directions);
            }
        });

        registerForContextMenu(ordersRecyclerView);

        return root;
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.orderscontextmenu, menu);
        menu.setHeaderTitle("Select Action");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.delete:
                ordersViewModel.DeleteOrder(longClickedOrder);
                return false;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void setLongClickedOrder(Order order) {
        longClickedOrder = order;
    }
}