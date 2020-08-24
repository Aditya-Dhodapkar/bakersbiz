package com.adidev.bakersbiz.model;

import java.util.ArrayList;
import java.util.List;

public class MockStore implements Store {
    List<Customer> list;
    public MockStore(){
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

    private Customer MakeCustomer(){
        Customer customer = new Customer((long) 23232, "Aditya", "9581466605");
        return customer;
    }
}
