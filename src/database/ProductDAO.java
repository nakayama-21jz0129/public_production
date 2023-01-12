package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO extends AbstractDAO {
    
    public ProductDAO() {
        super();
    }

    /**
     * 全商品を返す。
     * @param bool
     * @return
     */
    public ArrayList<ProductDTO> getArray(boolean bool) {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        ArrayList<ProductDTO> productDTOArray = new ArrayList<>();

        String sql = "SELECT id, name, price, product_class_id, use_flag "
                + "FROM products";
        if (bool) {
            sql += " WHERE use_flag = 1";
        }

        try {
            pStmt = getConn().prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                productDTOArray.add(
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

        return productDTOArray;
    }
    
    /**
     * 商品をデータベースに登録する。
     *  一意制約に違反した場合は-1を返す
     * @param name
     * @param price
     * @param productClassId
     * @param useFlag
     * @return
     */
    public int reg(String name, int price, int productClassId, int useFlag) {
        PreparedStatement pStmt = null;
        int row = 0;

        String sql = "INSERT INTO products(name, price, product_class_id, use_flag) "
                + "VALUES(?, ?, ?, ?)";

        try {
            pStmt = getConn().prepareStatement(sql);
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
     * idの一致する商品を削除する。
     * @param id
     * @return
     */
    public int del(int id) {
        PreparedStatement pStmt = null;
        int row = 0;

        String sql = "DELETE FROM products "
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
     * 使ってる？
     * 
     * @param id
     * @return
     */
    public ProductDTO searchProduct(int id) {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        ProductDTO productDTO = null;

        String sql = "SELECT id, name, price, product_class_id, use_flag "
                + "FROM products "
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
