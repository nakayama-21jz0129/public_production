package beans;

import java.util.ArrayList;

import model.Employee;
import model.Order;

public class BeanForOrderConfirm extends AbstractBean {
    // 表示用
    private ArrayList<Order> orderArray;

    /**
     * 引数ありコンストラクタ
     * @param employee
     */
    public BeanForOrderConfirm(Employee employee) {
        super(employee);
    }

    public ArrayList<Order> getOrderArray() {
        return orderArray;
    }

    public void setOrderArray(ArrayList<Order> orderArray) {
        this.orderArray = orderArray;
    }

    
}
