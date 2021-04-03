package com.adidev.bakersbiz.ui.menu;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.adidev.bakersbiz.model.MenuItem;
import com.adidev.bakersbiz.repository.Repository;
import com.adidev.bakersbiz.ui.Orders.OrdersDataAdapter;

public class MenuViewModel extends ViewModel {

    private MutableLiveData<RecyclerView.Adapter> menuData;
    private Repository repo;
    private Fragment associatedFragment;

    public MenuViewModel(Repository repo, Fragment associatedFragment) {
        this.repo = repo;
        this.associatedFragment = associatedFragment;
        RecyclerView.Adapter orderAdapter = new OrdersDataAdapter(repo, associatedFragment);
        menuData = new MutableLiveData<>();
        menuData.setValue(orderAdapter);
    }

    public LiveData<RecyclerView.Adapter> getMenuData() {return menuData;}

    public boolean addMenuItem(MenuItem item){
        repo.addToMenu(item);
        //This should update the list UI to refresh itself if the underlying data in the store changed.
        menuData.getValue().notifyDataSetChanged();

        return true;
    }

    public boolean updateMenuItem(MenuItem item){
        repo.updateMenuItem(item);
        //This should update the list UI to refresh itself if the underlying data in the store changed.
        menuData.getValue().notifyDataSetChanged();
        return true;
    }

    public boolean deleteMenuItem(MenuItem item){
        repo.deleteMenuItem(item);
        //This should update the list UI to refresh itself if the underlying data in the store changed.
        menuData.getValue().notifyDataSetChanged();
        return true;
    }
}