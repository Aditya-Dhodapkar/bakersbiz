package com.adidev.bakersbiz.ui.menudetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.adidev.bakersbiz.model.Menu;
import com.adidev.bakersbiz.model.MenuItem;
import com.adidev.bakersbiz.model.Order;
import com.adidev.bakersbiz.repository.Repository;

public class MenuDetailsViewModel extends ViewModel {
    private MutableLiveData<MenuItem> menuItemData;
    private Repository repository;

    public MenuDetailsViewModel(Repository repository, MenuItem menuItem) {
        this.repository = repository;
        menuItemData = new MutableLiveData<>(menuItem);
    }

    public LiveData<MenuItem> getMenu() {
        return menuItemData;
    }
}