package com.adidev.bakersbiz.model;
import java.util.List;

public interface Store {
    List<Customer> getCustomers();
    List<Order> getOrders();
    Menu getMenu();
    Inventory getInventory();
    boolean addNewCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    boolean deleteCustomer (Customer customer);
    boolean addToMenu(MenuItem item);
    boolean updateMenuItem(MenuItem item);
    boolean deleteMenuItem(MenuItem item);
    boolean addNewOrder(Order order);
    boolean updateOrder(Order order);
    boolean deleteOrder(Order order);
    boolean addItemToInventory(Ingredient item);
    boolean updateItemInInventory(Ingredient item);
    boolean deleteItemToInventory(Ingredient item);
 }
