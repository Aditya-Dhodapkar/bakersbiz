package com.adidev.bakersbiz.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MockStore implements Store {
    private List<Customer> list;
    private List<Order> orderList;
    private String orderListStore = "//orders.txt";
    private String customerListStore = "//customer.txt";
    private String menuListStore = "//menu.txt";
    //Only 1 menu for now.
    private Menu menu;
    private String filesDir;
    public MockStore(String filesDir) {
        this.filesDir = filesDir;
        list = ReadDataFromFile(filesDir + customerListStore);
        orderList = ReadDataFromFile(filesDir + orderListStore);
        List<MenuItem> menuItems = ReadDataFromFile(filesDir + menuListStore);
        if(menuItems  != null)
            menu = new Menu(menuItems);

        if(list == null)
            list = new ArrayList<Customer>();
        else
            list = SortCustomerListsBasedOnName(list);
        if(menu == null)
            menu = new Menu();
        if(orderList == null)
            orderList = new ArrayList<Order>();
        else
            orderList = SortOrderListsBasedOnDate(orderList);
    }

    @Override
    public List<Customer> getCustomers() {
        return  list;
    }

    @Override
    public List<Order> getOrders() {
        return orderList;
    }

    @Override
    public Menu getMenu() {
        return menu;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    @Override
    public boolean addNewCustomer(Customer customer) {
        list.add(customer);
        List<Customer> custList = SortCustomerListsBasedOnName(list);
        SaveListsToStore(filesDir + customerListStore, list);
        List list = ReadDataFromFile(filesDir + customerListStore);
        return true;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        Customer entry = getCustomer(customer.getCustomerID());
        entry.setCustomerNotes(customer.getNotes());
        SaveListsToStore(filesDir + customerListStore, list);
        return true;
    }

    @Override
    public boolean deleteCustomer(Customer customer) {
        int index = getIndexOfCustomer(customer);
        list.remove(index);
        SaveListsToStore(filesDir + customerListStore, list);
        return true;
    }

    @Override
    public boolean addToMenu(MenuItem item) {
         menu.addMenuItem(item);
         SaveListsToStore(filesDir + menuListStore, menu.getMenuItems());
         return true;
    }

    @Override
    public boolean updateMenuItem(MenuItem item) {
        menu.updateMenuItem(item);
        SaveListsToStore(filesDir + menuListStore, menu.getMenuItems());
        return true;
    }

    @Override
    public boolean deleteMenuItem(MenuItem item) {
        menu.deleteMenuItem(item);
        SaveListsToStore(filesDir + menuListStore, menu.getMenuItems());
        return true;
    }

    @Override
    public boolean addNewOrder(Order order) {
        if(order.getOrderID() == -1) {
            order.setOrderID(new Random().nextInt(1000));
            orderList.add(order);
            orderList = SortOrderListsBasedOnDate(orderList);
            SaveListsToStore(filesDir + orderListStore, orderList);
        }
        return true;
    }

    @Override
    public boolean updateOrder(Order order) {
        int orderPosition = getOrderPosition(order.getOrderID());
        orderList.remove(orderPosition);
        orderList.add(order);
        orderList = SortOrderListsBasedOnDate(orderList);
        SaveListsToStore(filesDir + orderListStore, orderList);
        return true;
    }

    @Override
    public boolean deleteOrder(Order order) {
        int orderPosition = getOrderPosition(order.getOrderID());
        orderList.remove(orderPosition);
        orderList = SortOrderListsBasedOnDate(orderList);
        SaveListsToStore(filesDir + orderListStore, orderList);
        return true;
    }

    @Override
    public Order getOrder(int orderId) {
        for(int i =0; i < orderList.size(); i++){
            if(orderList.get(i).getOrderID() == orderId)
                return orderList.get(i);
        }

        return null;
    }

    @Override
    public boolean addItemToInventory(Ingredient item) {
        return false;
    }

    @Override
    public boolean updateItemInInventory(Ingredient item) {
        return false;
    }

    @Override
    public boolean deleteItemToInventory(Ingredient item) {
        return false;
    }

    @Override
    public Customer getCustomer(int customerId) {

        for(int i=0;i<list.size();i++){
            Customer customer = list.get(i);
            if(customer.getCustomerID() == customerId)
                return customer;
        }

        return null;
    }

    private Customer MakeCustomer(){
        Customer customer = new Customer((long) 23232, "Aditya", "9581466605");
        return customer;
    }

    private int getIndexOfCustomer(Customer customerToFind){
        for(int i=0;i<list.size();i++){
            Customer customer = list.get(i);
            if(customer.getCustomerID() == customerToFind.getCustomerID())
                return i;
        }
        return -1;//not found.
    }

    //This function saves the all the objects in the list that is passed
    //to the function at the given filePath.
    private boolean SaveListsToStore(String filePath, List listToSave) {
        ObjectOutputStream stream = null;
        boolean operationStatus = false;
        try {
            File file = new File(filePath);
            //will create a new file only if a file does'nt exist.
            Boolean newFile = file.createNewFile();
            stream = new ObjectOutputStream(new FileOutputStream(file));
            stream.writeObject(listToSave);
        }
        catch(FileNotFoundException exp){
            exp.printStackTrace();
        }
        catch(IOException exp){
            exp.printStackTrace();
        }
        finally {
            if(stream != null) {
                try {
                    stream.close();
                    operationStatus = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return operationStatus;
    }

    //This function reads the stores data from the list.
    private List ReadDataFromFile(String filePath)
    {
        ObjectInputStream stream = null;
        List list = null;
        boolean operationStatus = false;

        try {
            stream = new ObjectInputStream(new FileInputStream(filePath));
            list = (ArrayList) stream.readObject();
        }
        catch (ClassNotFoundException exp){
            exp.printStackTrace();
        }
        catch(FileNotFoundException exp){
            exp.printStackTrace();
        }
        catch(IOException exp){
            exp.printStackTrace();
        }
        finally {
            if(stream != null) {
                try {
                    stream.close();
                    operationStatus = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private List<Customer> SortCustomerListsBasedOnName(List<Customer> list) {
        int i, j, min;
        int listSize = list.size();
        // Use selection sort to sort customers by their name.
        for (i = 0; i < listSize -1; i++)
        {
            // Look for the minimum element in the list
            min = i;
            Customer cust2 = list.get(min);

            for (j = i+1; j < listSize; j++) {
                Customer cust1 = list.get(j);
                if (cust1.getName().compareToIgnoreCase(cust2.getName()) < 0)
                    min = j;
            }
            //Swap
            Customer temp = list.get(i);
            list.set(i, list.get(min));
            list.set(min, temp);
        }
        return list;
    }

    private List<Order> SortOrderListsBasedOnDate(List<Order> list) {
        int i, j, min;
        int listSize = list.size();
        // Use selection sort to sort customers by their name.
        for (i = 0; i < listSize -1; i++)
        {
            // Look for the minimum element in the list
            min = i;
            Order order2 = list.get(min);

            for (j = i+1; j < listSize; j++) {
                Order order1 = list.get(j);
                if (order1.getOrderDeliveryDate().compareTo(order2.getOrderDeliveryDate()) > 0)
                    min = j;
            }
            //Swap
            Order temp = list.get(i);
            list.set(i, list.get(min));
            list.set(min, temp);
        }
        return list;
    }

    private int getOrderPosition(int orderId) {
        int orderPos = -1;
        for(int i =0; i < orderList.size(); i++){
            orderPos = i;
            if(orderList.get(i).getOrderID() == orderId)
                break;
        }

        return orderPos;
    }

}
