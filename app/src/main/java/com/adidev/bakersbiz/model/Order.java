package com.adidev.bakersbiz.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Order implements Serializable {
    private int orderID;
    private int customerID;
    private int price;
    private String customerName;
    private MenuItem item;
    private int discounts;
    private int numberOfItems;
    private OrderStatus status;
    private LocalDateTime orderDeliveryDate;
    private LocalDateTime orderPlacedDate;
    private String orderDescription;

    public Order (int orderID, int customerID, int price, String customerName, MenuItem item, int discounts, int numberOfItems, LocalDateTime orderDeliveryDate)    {
        this.orderID = orderID;
        this.customerID = customerID;
        this.customerName = customerName;
        this.item = item;
        this.discounts = discounts;
        this.numberOfItems = numberOfItems;
        this.orderDeliveryDate = orderDeliveryDate;
        this.price = price;
    }
    public int getCustomerID() {
        return customerID;
    }

    public int getOrderID() {
        return orderID;
    }

    public LocalDateTime getOrderDeliveryDate() {
        return orderDeliveryDate;
    }

    public MenuItem getItem() {
        return item;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public String getCustomerName() {
        return customerName;
    }

    public int getPrice() {
        return price;
    }
    public int getNumberOfItems() {
        return numberOfItems;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setItem(MenuItem item) {
        this.item = item;
    }

    public void setOrderDeliveryDate(LocalDateTime orderDeliveryDate) {
        this.orderDeliveryDate = orderDeliveryDate;
    }

    public void setOrderPlacedDate(LocalDateTime orderPlacedDate) {
        this.orderPlacedDate = orderPlacedDate;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }
}
