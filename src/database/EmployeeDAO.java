package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAO extends AbstractDAO{

    public EmployeeDAO() {
        super();
    }

    /**
     * 名前とパスワードから従業員を探す。
     *  見つからない場合はnullを返す
     * @param name
     * @param password
     * @return
     */
    public EmployeeDTO search(String name, String password) {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        EmployeeDTO employeeDTO = null;

        String sql = "SELECT id, name, manager_flag, use_flag "
                + "FROM employees "
                + "WHERE name = ? "
                + "AND password = ?";

        try {
            pStmt = getConn().prepareStatement(sql);
            pStmt.setString(1, name);
            pStmt.setString(2, password);
            rs = pStmt.executeQuery();
            if (rs.next()) {
                employeeDTO = new EmployeeDTO(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("manager_flag"),
                        rs.getInt("use_flag"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (pStmt != null)
                    pStmt.close();
                if (rs != null)
                    rs.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return employeeDTO;
    }
    
    /**
     * 識別子から従業員を探す。
     *  見つからない場合はnullを返す
     * @param id
     * @return
     */
    public EmployeeDTO search(int id) {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        EmployeeDTO employeeDTO = null;

        String sql = "SELECT id, name, manager_flag, use_flag "
                + "FROM employees "
                + "WHERE id = ?";

        try {
            pStmt = getConn().prepareStatement(sql);
            pStmt.setInt(1, id);
            rs = pStmt.executeQuery();
            if (rs.next()) {
                employeeDTO = new EmployeeDTO(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("manager_flag"),
                        rs.getInt("use_flag"));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (pStmt != null)
                    pStmt.close();
                if (rs != null)
                    rs.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return employeeDTO;
    }

    /**
     * 全従業員を返す。
     * @return
     */
    public ArrayList<EmployeeDTO> getArray() {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        ArrayList<EmployeeDTO> employeeDTOArray = new ArrayList<>();

        String sql = "SELECT id, name, manager_flag, use_flag "
                + "FROM employees";

        try {
            pStmt = getConn().prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                employeeDTOArray.add(
                        new EmployeeDTO(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getInt("manager_flag"),
                                rs.getInt("use_flag")));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (pStmt != null)
                    pStmt.close();
                if (rs != null)
                    rs.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return employeeDTOArray;
    }
    
    /**
     * 従業員をデータベースに登録する。
     *  一意制約に違反した場合は-1を返す
     * @param name
     * @param password
     * @param managerFlag
     * @param useFlag
     * @return
     */
    public int reg(String name, String password, int managerFlag, int useFlag) {
        PreparedStatement pStmt = null;
        int row = 0;

        String sql = "INSERT INTO employees(name, password, manager_flag, use_flag) "
                + "VALUES(?, ?, ?, ?)";

        try {
            pStmt = getConn().prepareStatement(sql);
            pStmt.setString(1, name);
            pStmt.setString(2, password);
            pStmt.setInt(3, managerFlag);
            pStmt.setInt(4, useFlag);
            row = pStmt.executeUpdate();
            
        }
        catch(SQLException se) {
            if (se.getErrorCode() == 1) {
                row = -1;
            }
            se.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (pStmt != null)
                    pStmt.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return row;
    }
    
    /**
     * idの一致する従業員を削除する。
     * @param id
     * @return
     */
    public int del(int id) {
        PreparedStatement pStmt = null;
        int row = 0;

        String sql = "DELETE FROM employees "
                + "WHERE id = ?";

        try {
            pStmt = getConn().prepareStatement(sql);
            pStmt.setInt(1, id);
            row = pStmt.executeUpdate();
            
        }
        catch(SQLException se) {
            se.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (pStmt != null)
                    pStmt.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return row;
    }
}
