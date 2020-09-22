package com.adidev.bakersbiz.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Order implements Serializable {
    private int orderID;
    private int customerID;
    private MenuItem item;
    private int discounts;
    private int numberOfItems;
    private OrderStatus status;
    private LocalDateTime orderDeliveryDate;
    private LocalDateTime orderPlacedDate;
}