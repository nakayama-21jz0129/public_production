package beans;

import java.util.ArrayList;

import model.Employee;

public class BeanForEmployeeManage extends AbstractBean{
    private ArrayList<Employee> employeeList = new ArrayList<>();

    public BeanForEmployeeManage(Employee employee) {
        super(employee);
    }

    public ArrayList<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList() {
        this.employeeList = getEmployee().getEmployeeList();
    }

}
