package beans;

import model.Employee;

public abstract class AbstractBean {
    private final String COPYRIGHT = "&copy; G10 2022";
    private final String VERSION = "v0.4.1";
    private Employee employee;

    public AbstractBean(Employee employee) {
        this.employee = employee;
    }

    // getter・setter
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getCopyright() {
        return COPYRIGHT;
    }

    public String getVersion() {
        return VERSION;
    }

}
