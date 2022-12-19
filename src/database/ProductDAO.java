package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO {
    private Connection conn;

    public ProductDAO() {
        DbManager dm = DbManager.getDbConMgr();
        conn = dm.getConn();
    }
    
    /**
     * 
     * @return
     */
    public ArrayList<ProductDTO> getProductList(boolean bool) {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        ArrayList<ProductDTO> productDTOList = new ArrayList<>();

        String sql = "SELECT id, name, price, product_class_id, use_flag "
                + "FROM product";
        if (bool) {
            sql += " WHERE use_flag = 1";
        }

        try {
            pStmt = conn.prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                productDTOList.add(
                        new ProductDTO(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getInt("price"),
                                rs.getInt("product_class_id"),
                                rs.getInt("use_flag")));
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

        return productDTOList;
    }
    
    /**
     * 
     * @param name
     * @param price
     * @param productClassId
     * @param useFlag
     * @return
     */
    public int regProduct(String name, int price, int productClassId, int useFlag) {
        PreparedStatement pStmt = null;
        int row = 0;

        String sql = "INSERT INTO product(name, price, product_class_id, use_flag) "
                + "VALUES(?, ?, ?, ?)";

        try {
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, name);
            pStmt.setInt(2, price);
            pStmt.setInt(3, productClassId);
            pStmt.setInt(4, useFlag);
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
     * 
     * @param id
     * @return
     */
    public int delProduct(int id) {
        PreparedStatement pStmt = null;
        int row = 0;

        String sql = "DELETE FROM product "
                + "WHERE id = ?";

        try {
            pStmt = conn.prepareStatement(sql);
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
     * 
     * @param id
     * @return
     */
    public ProductDTO searchProduct(int id) {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        ProductDTO productDTO = null;

        String sql = "SELECT id, name, price, product_class_id, use_flag "
                + "FROM product "
                + "WHERE id = ?";

        try {
            pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, id);
            rs = pStmt.executeQuery();
            if (rs.next()) {
                productDTO = new ProductDTO(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("product_class_id"),
                        rs.getInt("use_flag"));
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

        return productDTO;
    }
}
