package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAO {
    private Connection conn;

    public EmployeeDAO() {
        DbManager dm = DbManager.getDbConMgr();
        conn = dm.getConn();
    }

    /**
     * 
     * @param name
     * @param password
     * @return
     */
    public EmployeeDTO searchEmployee(String name, String password) {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        EmployeeDTO employeeDTO = null;

        String sql = "SELECT id, name, manager_flag, use_flag "
                + "FROM employee "
                + "WHERE name = ? "
                + " AND password = ?";

        try {
            pStmt = conn.prepareStatement(sql);
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
     * 
     * @return
     */
    public ArrayList<EmployeeDTO> getEmployeeList() {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        ArrayList<EmployeeDTO> employeeDTOList = new ArrayList<>();

        String sql = "SELECT id, name, manager_flag, use_flag "
                + "FROM employee";

        try {
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                employeeDTOList.add(
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

        return employeeDTOList;
    }
    
    /**
     * 
     * @param name
     * @param password
     * @return
     */
    public int regEmployee(String name, String password, int managerFlag, int useFlag) {
        PreparedStatement pStmt = null;
        int row = 0;

        String sql = "INSERT INTO employee(name, password, manager_flag, use_flag) "
                + "VALUES(?, ?, ?, ?)";

        try {
            pStmt = conn.prepareStatement(sql);
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
     * 
     * @param id
     * @return
     */
    public int delEmployee(int id) {
        PreparedStatement pStmt = null;
        int row = 0;

        String sql = "DELETE FROM employee "
                + "WHERE id = ?";

        try {
            pStmt = conn.prepareStatement(sql);
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
