package com.adidev.bakersbiz.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavDirections;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.adidev.bakersbiz.GlobalClass;
import com.adidev.bakersbiz.R;
import com.adidev.bakersbiz.model.Customer;
import com.adidev.bakersbiz.model.MenuItem;
import com.adidev.bakersbiz.ui.customers.CustomerViewModel;
import com.adidev.bakersbiz.ui.menudetails.MenuDetailsFragmentDirections;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MenuFragment extends Fragment {

    private MenuViewModel menuViewModel;
    private RecyclerView menuRecyclerView;
    private RecyclerView.LayoutManager menuLayoutManager;
    private MenuItem menuItem;


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

        menuRecyclerView = (RecyclerView) root.findViewById(R.id.menu_list_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        menuRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        menuLayoutManager = new LinearLayoutManager(getContext());

        menuRecyclerView.setLayoutManager(menuLayoutManager);

        // specify an adapter (see also next example)
        menuRecyclerView.setAdapter(menuViewModel.getMenuData().getValue());

        //https://gist.github.com/evandrix/7058235

        final Fragment fragment = this;
        FloatingActionButton fab = root.findViewById(R.id.fab_menu);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String menuItemName = "";
                if(menuItem != null)
                    menuItemName = menuItem.getName();
                MenuFragmentDirections.NavigateToMenuDetail directions = MenuFragmentDirections.navigateToMenuDetail(menuItemName);
                NavHostFragment.findNavController(fragment).navigate(directions);
            }
        });

        registerForContextMenu(menuRecyclerView);

        return root;
    }
    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menucontextmenu, menu);
        menu.setHeaderTitle("Select Action");
    }

    @Override
    public boolean onContextItemSelected(@NonNull android.view.MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.delete:
                menuViewModel.deleteMenuItem(menuItem);
                return true;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, MenuToString());
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public void setLongClickedMenuItem(MenuItem item) {
        menuItem = item;
    }

    private String MenuToString() {
        List<MenuItem> list = ((GlobalClass)getContext().getApplicationContext()).getRepository().getMenu().getMenuItems();
        StringBuilder builder = new StringBuilder();
        for(int i =0;i<list.size();i++) {
            MenuItem item = list.get(i);
            builder.append(item.getName() + "\n");
            builder.append("₹" + String.valueOf(item.getPrice()) + "\n");
            builder.append(item.getDescription() + "\n\n");
        }
        return builder.toString();
    }
}