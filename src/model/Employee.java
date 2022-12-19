package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import beans.BeanForLogin;
import database.EmployeeDAO;
import database.EmployeeDTO;

public class Employee {
    private int id;
    private String name;
    private boolean managerFlag;
    private boolean useFlag;

    private BeanForLogin bean;

    public Employee(BeanForLogin bean) {
        this.bean = bean;
    }

    private Employee(int id, String name, boolean managerFlag, boolean useFlag) {
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

    /**
     * 
     * @return
     */
    public boolean loginEmployee() {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        EmployeeDTO employeeDTO = null;
        boolean bool = false;

        if (bean.getName() != null && bean.getPassword() != null) {
            if (bean.getName().length() <= 64 && bean.getPassword().length() == 64) {
                try {
                    MessageDigest digest = MessageDigest.getInstance("SHA-512");
                    byte[] passwordHash;
                    String password = bean.getPassword() + "xYZ3n4e&7";
                    passwordHash= digest.digest(password.getBytes());
                    for (int i = 0; i < 5; i++) {
                        passwordHash= digest.digest(passwordHash);
                    }
                    StringBuilder password16 = new StringBuilder(2 * passwordHash.length);
                    for(byte b: passwordHash) {
                        password16.append(String.format("%02X", b&0xff) );
                    }
                    
                    employeeDTO = employeeDAO.searchEmployee(bean.getName(), password16.toString());
                    if (employeeDTO != null) {
                        setId(employeeDTO.getId());
                        setName(employeeDTO.getName());
                        setManagerFlag(employeeDTO.isManagerFlag());
                        setUseFlag(employeeDTO.isUseFlag());
                        if (isUseFlag()) {
                            bool = true;
                        } else {
                            bean.setMsg("このアカウントは使用できません");
                        }
                    } else {
                        bean.setMsg("従業員名・パスワードが正しくありません");
                    }
                }
                catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                    bean.setMsg("処理に問題が発生しました");
                }
            } else {
                // フロントサイドで対処するので表示されない
                bean.setMsg("従業員名は64文字以内で入力してください");
            }
        } else {
            // フロントサイドで対処するので表示されない
            bean.setMsg("従業員名・パスワードを入力してください");
        }

        return bool;
    }

    /**
     * 
     * @return
     */
    public ArrayList<Employee> getEmployeeList() {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        ArrayList<EmployeeDTO> employeeDTOList = null;
        ArrayList<Employee> employeeList = new ArrayList<>();

        employeeDTOList = employeeDAO.getEmployeeList();

        for (EmployeeDTO dto : employeeDTOList) {
            employeeList.add(
                    new Employee(
                            dto.getId(),
                            dto.getName(),
                            dto.isManagerFlag(),
                            dto.isUseFlag()));
        }

        return employeeList;
    }
    
    /**
     * 
     * @return
     */
    public ArrayList<Map<String, Object>> getMapEmployeeList() {
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (Employee element: getEmployeeList()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", element.getId());
            map.put("name", element.getName());
            map.put("manager_flag", element.isManagerFlag());
            map.put("use_flag", element.isUseFlag());
            list.add(map);
        }
        return list;
    }

    /**
     * 
     * @param name
     * @param password
     * @param managerFlag
     * @param useFlag
     * @return
     */
    public Map<String, Object> regEmployee(String name, String password, boolean managerFlag, boolean useFlag) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Map<String, Object> map = new HashMap<>();
        int row;
        map.put("result", false);
        map.put("msg", "従業員の登録に失敗しました");
        
        if (name != null && password != null) {
            if (name.length() <= 64 && password.length() == 64) {
                try {
                    MessageDigest digest = MessageDigest.getInstance("SHA-512");
                    byte[] passwordHash;
                    password += "xYZ3n4e&7";
                    passwordHash= digest.digest(password.getBytes());
                    for (int i = 0; i < 5; i++) {
                        passwordHash= digest.digest(passwordHash);
                    }
                    StringBuilder password16 = new StringBuilder(2 * passwordHash.length);
                    for(byte b: passwordHash) {
                        password16.append(String.format("%02X", b&0xff) );
                    }
                    
                    row = employeeDAO.regEmployee(
                            name,
                            password16.toString(),
                            managerFlag ? 1 : 0, 
                            useFlag ? 1 : 0);
                    if (row == 1) {
                        map.replace("result", true);
                        map.replace("msg", "従業員の登録に成功しました");
                    }
                    else if (row == -1) {
                        map.replace("msg", "既に存在する従業員名は使用できません");
                    }
                }
                catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        }

        return map;

    }
    
    /**
     * 
     * @param id
     * @return
     */
    public Map<String, Object> delEmployee(int id) {
        EmployeeDAO employeeDAO = new EmployeeDAO();
        Map<String, Object> map = new HashMap<>();
        int row;
        map.put("result", false);
        map.put("msg", "従業員の削除に失敗しました");
        
        row = employeeDAO.delEmployee(id);
        if (row == 1) {
            map.replace("result", true);
            map.replace("msg", "従業員の削除に成功しました");
        }

        return map;

    }
}
