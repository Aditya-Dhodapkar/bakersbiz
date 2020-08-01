package com.adidev.bakersbiz.model;

import java.time.LocalDateTime;

public class Order {
    private int orderID;
    private MenuItem item;
    private int discounts;
    private int numberOfOrders;
    private OrderStatus status;
    private LocalDateTime orderDeliveryDate;
    private LocalDateTime orderPlacedDate;
}