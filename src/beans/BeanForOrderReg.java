package beans;

import java.util.Map;

import model.Employee;

public class BeanForOrderReg extends AbstractBean {
    // 表示用
    private Map<Integer, Map> productMapExt;
    private String timePeriodText;
    private int percentRate;

    /**
     * 引数ありコンストラクタ
     * @param employee
     */
    public BeanForOrderReg(Employee employee) {
        super(employee);
    }

    // getter・setter
    public Map<Integer, Map> getProductMapExt() {
        return productMapExt;
    }

    public void setProductMapExt(Map<Integer, Map> productMapExt) {
        this.productMapExt = productMapExt;
    }

    public String getTimePeriodText() {
        return timePeriodText;
    }

    public void setTimePeriodText(String timePeriodText) {
        this.timePeriodText = timePeriodText;
    }

    public int getPercentRate() {
        return percentRate;
    }

    public void setPercentRate(int percentRate) {
        this.percentRate = percentRate;
    }
}
