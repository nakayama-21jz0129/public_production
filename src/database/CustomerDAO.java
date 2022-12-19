package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAO {
    private Connection conn;

    public CustomerDAO() {
        DbManager dm = DbManager.getDbConMgr();
        conn = dm.getConn();
    }

    /**
     * 
     * @return
     */
    public ArrayList<CustomerDTO> getCustomerList() {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        ArrayList<CustomerDTO> customerDTOList = new ArrayList<>();

        String sql = "SELECT id, telephone_no, name, address, preference_product_id1, preference_product_id2 "
                + "FROM customer";

        try {
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                customerDTOList.add(
                        new CustomerDTO(
                                rs.getInt("id"),
                                rs.getString("telephone_no"),
                                rs.getString("name"),
                                rs.getString("address"),
                                rs.getInt("preference_product_id1"),
                                rs.getInt("preference_product_id2")));
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

        return customerDTOList;
    }
    
    public int regCustomer(String tel, String name, String address) {
        PreparedStatement pStmt = null;
        int row = 0;

        String sql = "INSERT INTO customer(telephone_no, name, address) "
                + "VALUES(?, ?, ?)";

        try {
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, tel);
            pStmt.setString(2, name);
            pStmt.setString(3, address);
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
    public int delCustomer(int id) {
        PreparedStatement pStmt = null;
        int row = 0;

        String sql = "DELETE FROM customer "
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
    
    /**
     * 
     * @param tel
     * @return
     */
    public CustomerDTO searchCustomer(String tel) {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        CustomerDTO customerDTO = null;

        String sql = "SELECT id, telephone_no, name, address, preference_product_id1, preference_product_id2 "
                + "FROM customer "
                + "WHERE telephone_no = ?";

        try {
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, tel);
            rs = pStmt.executeQuery();
            if (rs.next()) {
                customerDTO = new CustomerDTO(
                        rs.getInt("id"),
                        rs.getString("telephone_no"),
                        rs.getString("name"),
                        rs.getString("address"),
                        rs.getInt("preference_product_id1"),
                        rs.getInt("preference_product_id2"));
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

        return customerDTO;
    }
}
