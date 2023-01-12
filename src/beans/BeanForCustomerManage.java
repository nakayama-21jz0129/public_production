package beans;

import java.util.ArrayList;

import model.Customer;
import model.Employee;

public class BeanForCustomerManage extends AbstractBean {
    // 表示用
    private ArrayList<Customer> customerArray = new ArrayList<>();

    /**
     * 引数ありコンストラクタ
     * @param employee
     */
    public BeanForCustomerManage(Employee employee) {
        super(employee);
    }

    // getter・setter
    public ArrayList<Customer> getCustomerArray() {
        return customerArray;
    }

    public void setCustomerArray(ArrayList<Customer> customerArray) {
        this.customerArray = customerArray;
    }

}
