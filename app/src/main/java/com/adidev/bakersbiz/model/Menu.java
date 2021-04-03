package com.adidev.bakersbiz.model;

import java.io.Serializable;
import java.util.List;

public class Menu implements Serializable {
    private String name;
    private List<MenuItem> itemList;

    public List<MenuItem> getMenuItems() {
     return itemList;
    }

    public MenuItem getItemAtPos(int i){
        return itemList.get(i);
    }

    public MenuItem getItemWithName(String name){
        for (int i=0;i<itemList.size();i++) {
            if(itemList.get(i).getName() == name)
                return itemList.get(i);
        }

        return null;
    }

    public Boolean addMenuItem (MenuItem item) {
        itemList.add(item);
        return true;
    }

    public Boolean deleteMenuItem (MenuItem item){
        int itemToDelete = -1;
        for (int i=0;i<itemList.size();i++) {
            if(itemList.get(i).getName() == item.getName())
                itemToDelete = i;
        }
        itemList.remove(itemToDelete);
        return true;
    }

    public Boolean updateMenuItem(MenuItem updatedItem){
        MenuItem item = getItemWithName(updatedItem.getName());
        deleteMenuItem(item);
        addMenuItem(updatedItem);
        //todo sort itemsList
        return true;
    }
}
