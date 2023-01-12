package database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class TimePeriodDiscountDAO extends AbstractDAO {

    public TimePeriodDiscountDAO() {
        super();
    }

    /**
     * 日付から時間帯割引を探す。
     * @param date
     * @return
     */
    public ArrayList<TimePeriodDiscountDTO> search(Date date) {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        ArrayList<TimePeriodDiscountDTO> timePeriodDiscountDTOArray = new ArrayList<>();

        String sql = "SELECT time_period, start_date, end_date, rate "
                + "FROM time_period_discounts "
                + "WHERE start_date <= ? "
                + "AND ? <= NVL(end_date, ?)";
        
        try {
            pStmt = getConn().prepareStatement(sql);
            pStmt.setDate(1, date);
            pStmt.setDate(2, date);
            pStmt.setDate(3, date);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                timePeriodDiscountDTOArray.add(
                        new TimePeriodDiscountDTO(
                                rs.getDouble("time_period"),
                                rs.getDate("start_date"),
                                rs.getDate("end_date"),
                                rs.getDouble("rate")));
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
        
        return timePeriodDiscountDTOArray;
    }
}
