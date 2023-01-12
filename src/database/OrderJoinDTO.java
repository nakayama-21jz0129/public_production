package database;

import java.sql.Timestamp;

public class OrderJoinDTO {
    private int id;
    private Timestamp orderTime;
    private int billingPrice;
    private String status;
    private String customerName;
    private String customerTel;
    private String customerAddress;
    private String tempAddress;
    private String employeeName;
    private int employeeManagerFlag;

    public OrderJoinDTO(int id, Timestamp orderTime, int billingPrice, String status, String customerName,
            String customerTel, String customerAddress, String tempAddress, String employeeName,
            int employeeManagerFlag) {
        this.id = id;
        this.orderTime = orderTime;
        this.billingPrice = billingPrice;
        this.status = status;
        this.customerName = customerName;
        this.customerTel = customerTel;
        this.customerAddress = customerAddress;
        this.tempAddress = tempAddress;
        this.employeeName = employeeName;
        this.employeeManagerFlag = employeeManagerFlag;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getTempAddress() {
        return tempAddress;
    }

    public void setTempAddress(String tempAddress) {
        this.tempAddress = tempAddress;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getEmployeeManagerFlag() {
        return employeeManagerFlag;
    }

    public void setEmployeeManagerFlag(int employeeManagerFlag) {
        this.employeeManagerFlag = employeeManagerFlag;
    }

}
