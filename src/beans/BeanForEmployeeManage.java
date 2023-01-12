package beans;

import java.util.ArrayList;

import model.Employee;

public class BeanForEmployeeManage extends AbstractBean{
    // 表示用
    private ArrayList<Employee> employeeArray = new ArrayList<>();

    /**
     * 引数ありコンストラクタ
     * @param employee
     */
    public BeanForEmployeeManage(Employee employee) {
        super(employee);
    }

    // getter・setter
    public ArrayList<Employee> getEmployeeArray() {
        return employeeArray;
    }

    public void setEmployeeArray(ArrayList<Employee> employeeArray) {
        this.employeeArray = employeeArray;
    }
    
    
    
}
