package com.adidev.bakersbiz.ui.menu;
import android.content.ContentUris;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.adidev.bakersbiz.R;
import com.adidev.bakersbiz.model.Customer;
import com.adidev.bakersbiz.model.MenuItem;
import com.adidev.bakersbiz.ui.menudetails.MenuDetailsFragment;
import com.adidev.bakersbiz.ui.menudetails.MenuDetailsFragmentDirections;

import java.io.IOException;
import java.io.InputStream;

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public class MenuViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener , View.OnLongClickListener{
    private MenuItem item;
    private Fragment associatedFragment;
    // each data item is just a string in this case
    public View card;
    public TextView itemName;
    public  TextView itemPrice;
    public TextView itemDescription;
    public  ImageView itemImagePhoto;

    public MenuViewHolder(View v, Fragment associatedFragment) {
        super(v);
        card = v.findViewById(R.id.menu_item);
        itemName = (TextView)v.findViewById(R.id.item_name);
        itemPrice = (TextView)v.findViewById(R.id.item_price);
        itemDescription = (TextView)v.findViewById(R.id.item_description);
        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
        this.associatedFragment = associatedFragment;
    }


    public void setMenuItem(MenuItem item){
        this.item = item;
    }

    @Override
    public void onClick(View view) {
        MenuFragmentDirections.NavigateToMenuDetail directions = MenuFragmentDirections.navigateToMenuDetail(item.getName());
        NavHostFragment.findNavController(associatedFragment).navigate(directions);
    }

    @Override
    public boolean onLongClick(View view) {
        ((MenuFragment)associatedFragment).setLongClickedMenuItem(item);
        return false;
    }
}
