package database;

import java.sql.Timestamp;

public class OrderDTO {
    private int id;
    private Timestamp orderTime;
    private int totalPrice;
    private int billingPrice;
    private String status;
    private int customerId;
    private String tempAddress;
    private int employeeId;

    public OrderDTO(int id, Timestamp orderTime, int totalPrice, int billingPrice, String status, int customerId,
            String tempAddress, int employeeId) {
        this.id = id;
        this.orderTime = orderTime;
        this.totalPrice = totalPrice;
        this.billingPrice = billingPrice;
        this.status = status;
        this.customerId = customerId;
        this.tempAddress = tempAddress;
        this.employeeId = employeeId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getBillingPrice() {
        return billingPrice;
    }

    public void setBillingPrice(int billingPrice) {
        this.billingPrice = billingPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getTempAddress() {
        return tempAddress;
    }

    public void setTempAddress(String tempAddress) {
        this.tempAddress = tempAddress;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

}
