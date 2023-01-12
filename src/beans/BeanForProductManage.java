package beans;

import java.util.HashMap;
import java.util.Map;

import model.Employee;

public class BeanForProductManage extends AbstractBean {
    // 表示用
    private Map<Integer, Map> productMapExt = new HashMap<>();

    /**
     * 引数ありコンストラクタ
     * @param employee
     */
    public BeanForProductManage(Employee employee) {
        super(employee);
    }

    // getter・setter
    public Map<Integer, Map> getProductMapExt() {
        return productMapExt;
    }

    public void setProductMapExt(Map<Integer, Map> productMapExt) {
        this.productMapExt = productMapExt;
    }

}
