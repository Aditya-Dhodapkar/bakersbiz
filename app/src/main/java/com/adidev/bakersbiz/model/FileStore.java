package com.adidev.bakersbiz.model;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

//The FileStore class implements the Store interface. We can have
// multiple Stores can be of, the FileStore uses  a File.
public class FileStore implements Store {
    //This list holds the list of all customers.
    private List<Customer> customerList;
    //This list holds all the orders across all the customers
    private List<Order> orderList;
    //Stores the menu for the app user.
    //Menu object contains a list of MenuItem objects
    private Menu menu;
    //Constructor will initialize the FileStore class from a file on the
    //device persistent store. The constructor takes file path as an argument
    public FileStore (String folderPath) {
        InitiazeFileStoreFromPersistentStore(folderPath);
    }

    @Override
    public List<Customer> getCustomers() {
        return customerList;
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
        return false;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public boolean deleteCustomer(Customer customer) {
        return false;
    }

    @Override
    public boolean addToMenu(MenuItem item) {
        return false;
    }

    @Override
    public boolean updateMenuItem(MenuItem item) {
        return false;
    }

    @Override
    public boolean deleteMenuItem(MenuItem item) {
        return false;
    }

    @Override
    public boolean addNewOrder(Order order) {
        return false;
    }

    @Override
    public boolean updateOrder(Order order) {
        return false;
    }

    @Override
    public boolean deleteOrder(Order order) {
        return false;
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
        return null;
    }

    private void InitiazeFileStoreFromPersistentStore(String filePath) {
    }

    private void SaveCustomers(String filePath) throws IOException {

        ObjectOutputStream stream = null;
        try {
            stream = new ObjectOutputStream(new FileOutputStream(filePath));
            stream.writeObject(customerList);
        }
        catch(FileNotFoundException exp){
            exp.printStackTrace();
        }
        catch(IOException exp){
            exp.printStackTrace();
            throw exp;
        }
        finally {
            if(stream != null)
                stream.close();
        }
    }

    private void SaveOrders(){

    }

    private void SaveMenu(){

    }
}
