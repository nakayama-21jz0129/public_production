package beans;

import java.util.HashMap;
import java.util.Map;

import model.Employee;
import model.Product;
import model.Tax;

public class BeanForOrderReg extends AbstractBean {
    private Map<Integer, Map> productMap = new HashMap<>();
    private double taxRate;
    private Product product = new Product();
    private Tax tax = new Tax();

    public BeanForOrderReg(Employee employee) {
        super(employee);
    }

    public Map<Integer, Map> getProductMap() {
        return productMap;
    }

    public void setProductMap() {
        this.productMap = product.getProductMap(true);
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate() {
        this.taxRate = tax.getRate();
    }

    
}
