package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TaxDAO {
    private Connection conn;

    public TaxDAO() {
        DbManager dm = DbManager.getDbConMgr();
        conn = dm.getConn();
    }

    public double getTaxRate() {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        double rate = -1.0;

        String sql = "SELECT rate "
                + "FROM tax "
                + "WHERE start_date <= CURRENT_DATE "
                + "AND CURRENT_DATE <= NVL(end_date, CURRENT_DATE)";
        
        try {
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                rate = rs.getDouble("rate");
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
        
        return rate;
    }
}
