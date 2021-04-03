package com.adidev.bakersbiz.repository;
import com.adidev.bakersbiz.model.Customer;
import com.adidev.bakersbiz.model.Ingredient;
import com.adidev.bakersbiz.model.Inventory;
import com.adidev.bakersbiz.model.Menu;
import com.adidev.bakersbiz.model.MenuItem;
import com.adidev.bakersbiz.model.MockStore;
import com.adidev.bakersbiz.model.Order;
import com.adidev.bakersbiz.model.Store;
import java.util.List;

/*
This class is used by the ViewModel
* */
public class Repository {

    private Store store;

    public  Repository(String filesDir) {

        store = new MockStore(filesDir);
    }
    public List<Customer> getCustomers() {
        return store.getCustomers();
    }

    public List<Order> getOrders() {
        return store.getOrders();
    }

    public Menu getMenu() {
        return store.getMenu();
    }

    public Inventory getInventory() {
        return null;
    }

    public boolean addNewCustomer(Customer customer) {
        return store.addNewCustomer(customer);
    }

    public boolean updateCustomer(Customer customer) {
        store.updateCustomer(customer);
        return true;
    }

    public boolean deleteCustomer(Customer customer) {
        return false;
    }

    public boolean addToMenu(MenuItem item) {
        return false;
    }

    public boolean updateMenuItem(MenuItem item) {
        return false;
    }

    public boolean deleteMenuItem(MenuItem item) {
        return false;
    }

    public boolean addNewOrder(Order order) {
        return false;
    }

    public boolean updateOrder(Order order) {
        return false;
    }

    public boolean deleteOrder(Order order) {
        return false;
    }

    public Order getOrder(int orderID) {
        return store.getOrder(orderID);
    }

    public boolean addItemToInventory(Ingredient item) {
        return false;
    }

    public boolean updateItemInInventory(Ingredient item) {
        return false;
    }

    public boolean deleteItemToInventory(Ingredient item) {
        return false;
    }

    public Customer getCustomer(int customerId) {
        return store.getCustomer(customerId);
    }

    public void DeleteCustomer(Customer customer) {
        store.deleteCustomer(customer);
    }
}
