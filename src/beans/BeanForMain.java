package beans;

import model.Employee;

public class BeanForMain {
    private Employee employee;

    public BeanForMain(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
