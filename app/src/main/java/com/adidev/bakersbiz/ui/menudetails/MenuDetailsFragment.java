package com.adidev.bakersbiz.ui.menudetails;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import com.adidev.bakersbiz.GlobalClass;
import com.adidev.bakersbiz.R;
import com.adidev.bakersbiz.model.MenuItem;
import com.adidev.bakersbiz.model.Order;
import com.adidev.bakersbiz.ui.menu.MenuFragmentDirections;
import com.adidev.bakersbiz.ui.orderdetails.OrderDetailsFragmentArgs;
import com.adidev.bakersbiz.ui.orderdetails.OrderDetailsFragmentDirections;
import com.adidev.bakersbiz.ui.orderdetails.OrderDetailsViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MenuDetailsFragment extends Fragment {

    private MenuDetailsViewModel menuDetailsViewModel;
    private MenuItem menuItem;
    private MenuDetailsFragmentArgs args;
    private View root;
    EditText menuName;
    EditText menuPrice;
    MultiAutoCompleteTextView menuItemDescription;
    Button submitOrder;

    public static MenuDetailsFragment newInstance() {
        return new MenuDetailsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = MenuDetailsFragmentArgs.fromBundle(requireArguments());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        inflater.inflate(R.layout.menu_details_fragment, container, false);
        Context context = getContext();
        Context applicationContext = context.getApplicationContext();

        menuItem = ((GlobalClass) applicationContext).getRepository().getMenu().getItemWithName(args.getMenuName());
        root = inflater.inflate(R.layout.menu_details_fragment, container, false);
        menuDetailsViewModel = new MenuDetailsViewModel(((GlobalClass) applicationContext).getRepository(), menuItem);

        final Fragment thisFragment = (Fragment)this;
        menuName = root.findViewById(R.id.menu_item_name);
        menuPrice = root.findViewById(R.id.menu_item_price);
        menuItemDescription = root.findViewById(R.id.menu_item_description);
        submitOrder = root.findViewById(R.id.menu_submit_details);
        submitOrder.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                final CharSequence descriptionText = menuItemDescription.getText();
                final CharSequence menuPriceText = menuPrice.getText();
                final CharSequence menuNameText = menuName.getText();

                if(menuItem != null) {
                     menuItem = new MenuItem(menuNameText.toString(), Integer.valueOf(menuPriceText.toString()), descriptionText.toString());
                    ((GlobalClass) getContext().getApplicationContext()).getRepository().updateMenuItem(menuItem);
                }
                else{
                    menuItem = new MenuItem(menuNameText.toString(), Integer.valueOf(menuPriceText.toString()), descriptionText.toString());
                    ((GlobalClass) getContext().getApplicationContext()).getRepository().addToMenu(menuItem);
                }

               NavDirections directions = MenuDetailsFragmentDirections.navigateToNotifications();
               NavHostFragment.findNavController(thisFragment).navigate(directions);
            }
        });

        if(menuItem != null) {
            menuName.setText(menuItem.getName());
            menuPrice.setText(String.valueOf(menuItem.getPrice()));
            menuItemDescription.setText(menuItem.getDescription());
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}