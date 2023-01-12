package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class OrderJoinDAO extends AbstractDAO {

    public OrderJoinDAO() {
        super();
    }
    
    /**
     * 全注文を返す。
     * @return
     */
    public ArrayList<OrderJoinDTO> getArray() {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        ArrayList<OrderJoinDTO> array = new ArrayList<>();

        String sql = "SELECT o.id o_i, o.order_time o_o, o.billing_price o_b, o.status o_s, o.temp_address o_t, c.name c_n, c.telephone_no c_t, c.address c_a, e.name e_n, e.manager_flag e_m "
                + "FROM orders o "
                + "LEFT OUTER JOIN customers c "
                + "ON o.customer_id = c.id "
                + "LEFT OUTER JOIN employees e "
                + "ON o.employee_id = e.id "
                + "ORDER BY o.order_time DESC";

        try {
            pStmt = getConn().prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                array.add(
                        new OrderJoinDTO(
                                rs.getInt("o_i"),
                                rs.getTimestamp("o_o"),
                                rs.getInt("o_b"),
                                rs.getString("o_s"),
                                rs.getString("c_n"),
                                rs.getString("c_t"),
                                rs.getString("c_a"),
                                rs.getString("o_t"),
                                rs.getString("e_n"),
                                rs.getInt("e_m")));
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

        return array;
    }
}
