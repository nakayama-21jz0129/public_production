package beans;

import model.Employee;

public class AbstractBean {
    private Employee employee;

    public AbstractBean(Employee employee) {
        this.employee = employee;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

}
