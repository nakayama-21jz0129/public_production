package model;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import conversion.Format;
import conversion.Password;
import database.EmployeeDAO;
import database.EmployeeDTO;

public class Employee {
    private int id;
    private String name;
    private boolean managerFlag;
    private boolean useFlag;

    /**
     * 引数無しコンストラクタ
     */
    public Employee() {
    }

    private Employee(int id, String name, boolean managerFlag, boolean useFlag) {
        setId(id);
        setName(name);
        setManagerFlag(managerFlag);
        setUseFlag(useFlag);
    }

    // getter・setter
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
     * 従業員のログイン処理をする。
     * @param name
     * @param password
     * @return
     */
    public boolean login(String name, String password) {
        
        // 引数のnullを確認
        if (name != null && password != null) {
            
            // 引数の文字数を確認
            if (name.length() <= 64 && password.length() == 64) {
                try {
                    // 従業員を探す
                    if (search(name, Password.hash(password))) {
                        
                        // 使用可能かを確認
                        if (isUseFlag()) {
                            return true;
                        }
                    }
                }
                // Password.hashメソッドからスロー
                catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }
    
    /**
     * 名前とパスワード(ハッシュ化済)から従業員を探す。
     *  見つかればフィールドにデータをセット
     * @param name
     * @param password
     * @return
     */
    public boolean search(String name, String password) {
        // DAO・DTO
        EmployeeDAO employeeDAO = new EmployeeDAO();
        EmployeeDTO employeeDTO = null;
        
        // 引数のnullを確認
        if (name != null && password != null) {
            
            // 引数の文字数を確認
            if (name.length() <= 64 && password.length() == 128) {
                
                // 従業員を探す
                employeeDTO = employeeDAO.search(name, password);
                if (employeeDTO != null) {
                    // フィールドに値をセット
                    setId(employeeDTO.getId());
                    setName(employeeDTO.getName());
                    setManagerFlag(Format.intToBoolean(employeeDTO.getManagerFlag()));
                    setUseFlag(Format.intToBoolean(employeeDTO.getUseFlag()));
                    
                    return true;
                }
            }
        }

        return false;
    }
    
    /**
     * 識別子から従業員を探す。
     *  見つかればフィールドにデータをセット
     * @param id
     * @return
     */
    public boolean search(int id) {
        // DAO・DTO
        EmployeeDAO employeeDAO = new EmployeeDAO();
        EmployeeDTO employeeDTO = null;
        
        // 引数を確認
        if (id != 0) {
                
            // 従業員を探す
            employeeDTO = employeeDAO.search(id);
            if (employeeDTO != null) {
                // フィールドに値をセット
                setId(employeeDTO.getId());
                setName(employeeDTO.getName());
                setManagerFlag(Format.intToBoolean(employeeDTO.getManagerFlag()));
                setUseFlag(Format.intToBoolean(employeeDTO.getUseFlag()));
                
                return true;
            }
        }

        return false;
    }

    /**
     * 全従業員をEmployee型配列で返す。
     * @return
     */
    public ArrayList<Employee> getArray() {
        // return用
        ArrayList<Employee> array = new ArrayList<>();
        
        // DAO
        EmployeeDAO employeeDAO = new EmployeeDAO();
        
        // EmployeeDTO型配列をEmployee型配列に変換
        for (EmployeeDTO dto : employeeDAO.getArray()) {
            array.add(
                    new Employee(
                            dto.getId(),
                            dto.getName(),
                            Format.intToBoolean(dto.getManagerFlag()),
                            Format.intToBoolean(dto.getUseFlag())));
        }

        return array;
    }
    
    /**
     * 全従業員を連想配列型配列で返す。
     * @deprecated
     * @see getMap
     * @return
     */
    public ArrayList<Map<String, Object>> getMapArray() {
        // return用
        ArrayList<Map<String, Object>> array = new ArrayList<>();
        
        // Employee型配列をMap<String, Object>型配列に変換
        for (Employee element: getArray()) {
            array.add(element.toMap());
        }
        
        return array;
    }

    /**
     * 従業員の登録処理をする。
     * @param name
     * @param password
     * @param managerFlag
     * @param useFlag
     * @return 
     */
    public int reg(String name, String password, boolean managerFlag, boolean useFlag) {
        // return用
        int result = 0;
        
        // DAO
        EmployeeDAO employeeDAO = new EmployeeDAO();
        
        // 引数のnullを確認
        if (name != null && password != null) {
            
            // 引数の文字数を確認
            if (name.length() <= 64 && password.length() == 64) {
                try {
                    // 従業員を登録
                    result = employeeDAO.reg(
                            name,
                            Password.hash(password),
                            Format.booleanToInt(managerFlag), 
                            Format.booleanToInt(useFlag));
                }
                // Password.hashメソッドからスロー
                catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;

    }
    
    /**
     * 従業員の削除処理をする。
     * @param id
     * @return
     */
    public boolean del(int id) {
        
        // DAO
        EmployeeDAO employeeDAO = new EmployeeDAO();
        
        // 従業員を削除
        if (Format.intToBoolean(employeeDAO.del(id))) {
            return true;
        }

        return false;

    }
    
    /**
     * クラスのデータを連想配列で返す。
     * @return
     */
    public Map<String, Object> toMap() {
        // return用
        Map<String, Object> map = new HashMap<>();
        
        // フィールドの値をセット
        map.put("id", getId());
        map.put("name", getName());
        map.put("manager_flag", isManagerFlag());
        map.put("use_flag", isUseFlag());
        
        return map;
    }
}
