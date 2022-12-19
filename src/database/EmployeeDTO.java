package database;

public class EmployeeDTO {
    private int id;
    private String name;
    private boolean managerFlag;
    private boolean useFlag;

    public EmployeeDTO(int id, String name, int managerFlag, int useFlag) {
        setId(id);
        setName(name);
        setManagerFlag(managerFlag == 1);
        setUseFlag(useFlag == 1);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isManagerFlag() {
        return managerFlag;
    }

    public void setManagerFlag(boolean managerFlag) {
        this.managerFlag = managerFlag;
    }

    public boolean isUseFlag() {
        return useFlag;
    }

    public void setUseFlag(boolean useFlag) {
        this.useFlag = useFlag;
    }
}
