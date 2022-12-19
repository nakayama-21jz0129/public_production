package beans;

import java.util.HashMap;
import java.util.Map;

import model.Employee;
import model.Product;

public class BeanForProductManage extends AbstractBean {
    private Map<Integer, Map> productMap = new HashMap<>();
    private Product product = new Product();

    public BeanForProductManage(Employee employee) {
        super(employee);
    }

    public Map<Integer, Map> getProductMap() {
        return productMap;
    }

    public void setProductMap() {
        this.productMap = product.getProductMap(false);
    }

}
