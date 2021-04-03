package com.adidev.bakersbiz.ui.menu;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.adidev.bakersbiz.R;
import com.adidev.bakersbiz.model.Customer;
import com.adidev.bakersbiz.model.MenuItem;
import com.adidev.bakersbiz.repository.Repository;

import static androidx.recyclerview.widget.RecyclerView.*;

public class MenuDataAdapter  extends Adapter {

    Repository repository;
    Fragment associatedFragment;
    public MenuDataAdapter(Object dataSet, Fragment associatedFragment) {
        repository = (Repository) dataSet;
        this.associatedFragment = associatedFragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_card_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        RecyclerView.ViewHolder vh = new MenuViewHolder(v, associatedFragment);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MenuItem item = repository.getMenu().getItemAtPos(position);
        ((MenuViewHolder)holder).itemName.setText(item.getName());
        ((MenuViewHolder)holder).itemPrice.setText(item.getPrice());
        ((MenuViewHolder)holder).itemPrice.setText(item.getDescription());
        ((MenuViewHolder)holder).setMenuItem(item);
    }
    @Override
    public int getItemCount() {
        return repository.getMenu().getMenuItems().size();
    }
}
