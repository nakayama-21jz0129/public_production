package database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbManager {

    private static DbManager dbManagerObj = null;
    private String oraHost = "";
    private String oraId = "";
    private String oraPw = "";
    private Connection conn = null;

    public synchronized static DbManager getDbConMgr() {
        if (DbManager.dbManagerObj == null) {
            DbManager.dbManagerObj = new DbManager();
        }
        return DbManager.dbManagerObj;
    }

    public Connection getConn() {
        return this.conn;
    }

    private DbManager() {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(oraHost, oraId, oraPw);
            System.out.println("接続完了");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("接続エラー");
        }
    }
}