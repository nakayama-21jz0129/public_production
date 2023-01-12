package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAO extends AbstractDAO{
    
    public CustomerDAO() {
        super();
    }
    
    /**
     * 全顧客を返す。
     * @return
     */
    public ArrayList<CustomerDTO> getArray() {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        ArrayList<CustomerDTO> customerDTOArray = new ArrayList<>();

        String sql = "SELECT id, telephone_no, name, address, preference_product_id1, preference_product_id2 "
                + "FROM customers";

        try {
            pStmt = getConn().prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                customerDTOArray.add(
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

        return customerDTOArray;
    }
    
    /**
     * 顧客をデータベースに登録する。
     *  一意制約に違反した場合は-1を返す
     * @param tel
     * @param name
     * @param address
     * @return
     */
    public int reg(String tel, String name, String address) {
        PreparedStatement pStmt = null;
        int row = 0;

        String sql = "INSERT INTO customers(telephone_no, name, address) "
                + "VALUES(?, ?, ?)";

        try {
            pStmt = getConn().prepareStatement(sql);
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
     * idの一致する顧客を削除する。
     * @param id
     * @return
     */
    public int del(int id) {
        PreparedStatement pStmt = null;
        int row = 0;

        String sql = "DELETE FROM customers "
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
    
    /**
     * 識別子から顧客を探す。
     *  見つからない場合はnullを返す
     * @param id
     * @return
     */
    public CustomerDTO search(int id) {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        CustomerDTO customerDTO = null;

        String sql = "SELECT id, telephone_no, name, address, preference_product_id1, preference_product_id2 "
                + "FROM customers "
                + "WHERE id = ?";

        try {
            pStmt = getConn().prepareStatement(sql);
            pStmt.setInt(1, id);
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
    
    /**
     * 電話番号から顧客を探す。
     *  見つからない場合はnullを返す
     * @param tel
     * @return
     */
    public CustomerDTO search(String tel) {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        CustomerDTO customerDTO = null;

        String sql = "SELECT id, telephone_no, name, address, preference_product_id1, preference_product_id2 "
                + "FROM customers "
                + "WHERE telephone_no = ?";

        try {
            pStmt = getConn().prepareStatement(sql);
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
