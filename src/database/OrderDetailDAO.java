package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.OrderDetail;

public class OrderDetailDAO extends AbstractDAO {

    public OrderDetailDAO() {
        super();
    }
    
    /**
     * 注文明細をデータベースに登録する。
     * @param order
     * @return
     * 引数でmodelを渡すのはいかがなものか
     */
    public int reg(int orderId, ArrayList<OrderDetail> orderDetailArray) {
        PreparedStatement pStmt = null;
        int row = 0;
        
        String sql = "INSERT INTO order_details(order_id, product_id, quantity) "
                + "VALUES(?, ?, ?)";
        
        try {
            pStmt = getConn().prepareStatement(sql);
            pStmt.setInt(1, orderId);
            for (OrderDetail detail : orderDetailArray) {
                pStmt.setInt(2, detail.getProductId());
                pStmt.setInt(3, detail.getQuantity());
                row += pStmt.executeUpdate();
            }
        }
        catch(SQLException se) {
            se.printStackTrace();
            row = -1;
        }
        catch (Exception e) {
            e.printStackTrace();
            row = -1;
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
     * 識別子から注文明細を探す。
     * @param id
     * @return
     */
    public ArrayList<OrderDetailDTO> search(int id) {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        ArrayList<OrderDetailDTO> orderDetailDTOArray = new ArrayList<>();

        String sql = "SELECT order_id, product_id, quantity "
                + "FROM order_details "
                + "WHERE order_id = ?";

        try {
            pStmt = getConn().prepareStatement(sql);
            pStmt.setInt(1, id);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                orderDetailDTOArray.add(
                        new OrderDetailDTO(
                                rs.getInt("order_id"),
                                rs.getInt("product_id"),
                                rs.getInt("quantity")));
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

        return orderDetailDTOArray;
    }
}
