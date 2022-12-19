package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductClassDAO {
    private Connection conn;

    public ProductClassDAO() {
        DbManager dm = DbManager.getDbConMgr();
        conn = dm.getConn();
    }
    
    /**
     * 
     * @return
     */
    public ArrayList<ProductClassDTO> getProductClassList() {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        ArrayList<ProductClassDTO> productClassDTOList = new ArrayList<>();

        String sql = "SELECT id, name "
                + "FROM product_class";

        try {
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                productClassDTOList.add(
                        new ProductClassDTO(
                                rs.getInt("id"),
                                rs.getString("name")));
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

        return productClassDTOList;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public int searchProductClass(String name) {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        int id = -1;

        String sql = "SELECT id "
                + "FROM product_class "
                + "WHERE name = ?";

        try {
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, name);
            rs = pStmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
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

        return id;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public int regProductClass(String name) {
        PreparedStatement pStmt = null;
        int row = 0;

        String sql = "INSERT INTO product_class(name) "
                + "VALUES(?)";

        try {
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, name);
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
}
