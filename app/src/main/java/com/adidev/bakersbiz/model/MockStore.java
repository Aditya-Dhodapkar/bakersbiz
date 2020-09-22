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

public class MockStore implements Store {
    private List<Customer> list;
    private String filesDir;
    public MockStore(String filesDir){
        this.filesDir = filesDir;
        list = new ArrayList<Customer>();
    }

    @Override
    public List<Customer> getCustomers() {
        return  list;
    }

    @Override
    public List<Order> getOrders() {
        return null;
    }

    @Override
    public Menu getMenu() {
        return null;
    }

    @Override
    public Inventory getInventory() {
        return null;
    }

    @Override
    public boolean addNewCustomer(Customer customer) {
        list.add(customer);
        List<Customer> custList = SortCustomerListsBasedOnName(list);
        SaveListsToStore(filesDir + "//customer.txt", list);
        List list = ReadDataFromFile(filesDir + "//customer.txt");
        return true;
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


    //This function saves the all the objects in the list that is passed
    //to the function at the given filePath.
    private boolean SaveListsToStore(String filePath, List listToSave) {
        ObjectOutputStream stream = null;
        boolean operationStatus = false;
        try {
            File file = new File(filePath);
            //will create a new file only if a file does'nt exist.
            file.createNewFile();
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
}
