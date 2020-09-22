package com.adidev.bakersbiz.model;

import java.io.Serializable;
import java.util.List;

/*
This class represents the Customer entity. It holds the customer information such as name, email, past and current orders etc.
* */
public class Customer implements Serializable {
	private String name;
	private String phone;
	private String email;
	private Long contactID;
    private int customerID;

    public Customer(Long contactID, String contactName, String contactNumber) {
        this.contactID = contactID;
        name = contactName;
        phone = contactNumber;
        customerID = contactID.intValue();
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    //Get a list of orders for this customer.
    public List<Order> getOrderList() {
        return null;
    }

    public List<CustomerLifeEvents> getCustomerLifeEvents(){
        return null;
    }

    public Long getContactID() {
        return contactID;
    }
    public int getCustomerID (){return customerID;}
    public void setContactID(Long contactID) {
        this.contactID = contactID;
    }
}
