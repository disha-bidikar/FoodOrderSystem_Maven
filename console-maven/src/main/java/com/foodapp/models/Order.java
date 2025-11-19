package com.foodapp.models;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Order {
    private int orderId;
    private String customerName;
    private String phone;
    private String address;
    private BigDecimal totalAmount;
    private Timestamp orderDate;

    public Order(int orderId, String customerName, String phone, String address, BigDecimal totalAmount, Timestamp orderDate) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.phone = phone;
        this.address = address;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
    }

    public int getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public Timestamp getOrderDate() { return orderDate; }

    @Override
    public String toString() {
        return orderId + " | " + customerName + " | " + phone + " | " + address + " | Total: $" + totalAmount + " | " + orderDate;
    }
}
