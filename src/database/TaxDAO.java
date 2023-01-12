package database;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TaxDAO extends AbstractDAO {

    public TaxDAO() {
        super();
    }

    /**
     * 日付から消費税を探す。
     *  見つからない場合はnullを返す
     * @param date
     * @return
     */
    public TaxDTO search(Date date) {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        TaxDTO taxDTO = null;

        String sql = "SELECT rate "
                + "FROM taxes "
                + "WHERE start_date <= ? "
                + "AND ? <= NVL(end_date, ?)";
        
        try {
            pStmt = getConn().prepareStatement(sql);
            pStmt.setDate(1, date);
            pStmt.setDate(2, date);
            pStmt.setDate(3, date);
            rs = pStmt.executeQuery();
            if (rs.next()) {
                taxDTO = new TaxDTO(rs.getDouble("rate"));
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
        
        return taxDTO;
    }
}
