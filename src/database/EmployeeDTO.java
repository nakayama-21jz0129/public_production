package database;

public class EmployeeDTO {
    private int id;
    private String name;
    private int managerFlag;
    private int useFlag;
    
    public EmployeeDTO(int id, String name, int managerFlag, int useFlag) {
        this.id = id;
        this.name = name;
        this.managerFlag = managerFlag;
        this.useFlag = useFlag;
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

    public int getManagerFlag() {
        return managerFlag;
    }

    public void setManagerFlag(int managerFlag) {
        this.managerFlag = managerFlag;
    }

    public int getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(int useFlag) {
        this.useFlag = useFlag;
    }

}
