package com.adidev.bakersbiz.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class Order implements Serializable {
    private int orderID;
    private int customerID;
    private int price;
    private String customerName;
    private MenuItem item;
    private String itemName;
    private int discounts;
    private int numberOfItems;
    private OrderStatus status;
    private Date orderDeliveryDate;
    private Date orderPlacedDate;
    private String orderDescription;

    public Order (int orderID, int customerID, int price, String customerName, String item, int discounts, int numberOfItems, Date orderDeliveryDate)    {
        this.orderID = orderID;
        this.customerID = customerID;
        this.customerName = customerName;
        this.itemName = item;
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

    public Date getOrderDeliveryDate() {
        return orderDeliveryDate;
    }

    public MenuItem getItem() {
        return item;
    }

    public String getItemName(){return itemName;}

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

    public void setOrderID(int orderID) {
        if(this.orderID == -1)
            this.orderID = orderID;
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

    public void setOrderDeliveryDate(Date orderDeliveryDate) {
        this.orderDeliveryDate = orderDeliveryDate;
    }

    public void setOrderPlacedDate(Date orderPlacedDate) {
        this.orderPlacedDate = orderPlacedDate;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }
}
