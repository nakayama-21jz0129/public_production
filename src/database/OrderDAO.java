package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class OrderDAO extends AbstractDAO {

    public OrderDAO() {
        super();
    }
    
    /**
     * 注文をデータベースに登録する。
     * @param order
     * @return
     */
    public int reg(Timestamp orderTime, int totalPrice, int billingPrice, int customerId, String tempAddress, int employeeId) {
        PreparedStatement pStmt = null;
        int orderId = 0;
        ResultSet rs = null;
        
        String sql = "LOCK TABLE orders IN EXCLUSIVE MODE";
        
        try {
            getConn().setAutoCommit(false);
            pStmt = getConn().prepareStatement(sql);
            pStmt.executeQuery();
            pStmt.close();
            
            sql = "INSERT INTO orders(order_time, total_price, billing_price, status, customer_id, temp_address, employee_id) "
                    + "VALUES(?, ?, ?, '注文済', ?, ?, ?)";
            
            pStmt = getConn().prepareStatement(sql);
            pStmt.setTimestamp(1, orderTime);
            pStmt.setInt(2, totalPrice);
            pStmt.setInt(3, billingPrice);
            pStmt.setInt(4, customerId);
            pStmt.setString(5, tempAddress);
            pStmt.setInt(6, employeeId);
            int row = pStmt.executeUpdate();
            pStmt.close();
            
            if (row == 1) {
                sql = "SELECT MAX(id) max_id "
                        + "FROM orders";
                
                pStmt = getConn().prepareStatement(sql);
                rs = pStmt.executeQuery();
                
                if (rs.next()) {
                    orderId = rs.getInt("max_id");
                }
            }   
            
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
                if (rs != null)
                    rs.close();
                getConn().commit();
                getConn().setAutoCommit(true);
            }
            catch(SQLException se) {
                se.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return orderId;
    }
    
    /**
     * idの一致する注文を削除する。
     * @param id
     * @return
     */
    public int del(int id) {
        PreparedStatement pStmt = null;
        int row = 0;

        String sql = "DELETE FROM orders "
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
     * 識別子から注文を探す。
     *  見つからない場合はnullを返す
     * @param id
     * @return
     */
    public OrderDTO search(int id) {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        OrderDTO orderDTO = null;

        String sql = "SELECT id, order_time, total_price, billing_price, status, customer_id, temp_address, employee_id "
                + "FROM orders "
                + "WHERE id = ?";

        try {
            pStmt = getConn().prepareStatement(sql);
            pStmt.setInt(1, id);
            rs = pStmt.executeQuery();
            if (rs.next()) {
                orderDTO = new OrderDTO(
                        rs.getInt("id"),
                        rs.getTimestamp("order_time"),
                        rs.getInt("total_price"),
                        rs.getInt("billing_price"),
                        rs.getString("status"),
                        rs.getInt("customer_id"),
                        rs.getString("temp_address"),
                        rs.getInt("employee_id"));
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

        return orderDTO;
    }
}
