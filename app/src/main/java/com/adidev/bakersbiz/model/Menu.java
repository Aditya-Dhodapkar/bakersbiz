package com.adidev.bakersbiz.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Serializable {
    private String name;
    private List<MenuItem> itemList;

    public Menu(){
        itemList = new ArrayList<MenuItem>();
    }

    public Menu(List<MenuItem> menuItems) {
        itemList = menuItems;
        itemList = SortCustomerListsBasedOnName(itemList);
    }

    public List<MenuItem> getMenuItems() {
     return itemList;
    }

    public MenuItem getItemAtPos(int i){
        return itemList.get(i);
    }

    public MenuItem getItemWithName(String name){
        for (int i=0;i<itemList.size();i++) {
            if(itemList.get(i).getName().compareToIgnoreCase(name) == 0)
                return itemList.get(i);
        }

        return null;
    }

    public Boolean addMenuItem (MenuItem item) {
        itemList.add(item);
        itemList = SortCustomerListsBasedOnName(itemList);
        return true;
    }

    public Boolean deleteMenuItem (MenuItem item){
        int itemToDelete = -1;
        for (int i=0;i<itemList.size();i++) {
            if(itemList.get(i).getName() == item.getName())
                itemToDelete = i;
        }
        itemList.remove(itemToDelete);
        itemList = SortCustomerListsBasedOnName(itemList);
        return true;
    }

    public Boolean updateMenuItem(MenuItem updatedItem){
        MenuItem item = getItemWithName(updatedItem.getName());
        deleteMenuItem(item);
        addMenuItem(updatedItem);
        itemList = SortCustomerListsBasedOnName(itemList);
        return true;
    }

    private List<MenuItem> SortCustomerListsBasedOnName(List<MenuItem> list) {
        int i, j, min;
        int listSize = list.size();
        // Use selection sort to sort customers by their name.
        for (i = 0; i < listSize -1; i++)
        {
            // Look for the minimum element in the list
            min = i;
            MenuItem cust2 = list.get(min);

            for (j = i+1; j < listSize; j++) {
                MenuItem cust1 = list.get(j);
                if (cust1.getName().compareToIgnoreCase(cust2.getName()) < 0)
                    min = j;
            }
            //Swap
            MenuItem temp = list.get(i);
            list.set(i, list.get(min));
            list.set(min, temp);
        }
        return list;
    }

}
