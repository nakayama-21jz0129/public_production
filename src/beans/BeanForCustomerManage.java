package beans;

import java.util.ArrayList;

import model.Customer;
import model.Employee;

public class BeanForCustomerManage extends AbstractBean {
    private ArrayList<Customer> customerList = new ArrayList<>();
    private Customer customer = new Customer();

    public BeanForCustomerManage(Employee employee) {
        super(employee);
    }

    public ArrayList<Customer> getCustomerList() {
        return customerList;
    }
 
    public void setCustomerList() {
        this.customerList = customer.getCustomerList();
    }
 
    
}
