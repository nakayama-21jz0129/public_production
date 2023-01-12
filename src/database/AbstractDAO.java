package database;

import java.sql.Connection;

public abstract class AbstractDAO {
    private Connection conn;

    public AbstractDAO() {
        DbManager dm = DbManager.getDbConMgr();
        conn = dm.getConn();
    }

    protected Connection getConn() {
        return conn;
    }

    protected void setConn(Connection conn) {
        this.conn = conn;
    }
}
