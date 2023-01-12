package database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductClassDAO extends AbstractDAO{
    
    public ProductClassDAO() {
        super();
    }

    /**
     * 全商品分類を返す。
     * @return
     */
    public ArrayList<ProductClassDTO> getArray() {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        ArrayList<ProductClassDTO> productClassDTOArray = new ArrayList<>();

        String sql = "SELECT id, name "
                + "FROM product_classes";

        try {
            pStmt = getConn().prepareStatement(sql);
            rs = pStmt.executeQuery();
            while (rs.next()) {
                productClassDTOArray.add(
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

        return productClassDTOArray;
    }

    /**
     * 名前から識別子を探す。
     *  見つからない場合は-1を返す
     * @param name
     * @return
     */
    public int search(String name) {
        PreparedStatement pStmt = null;
        ResultSet rs = null;
        int id = -1;

        String sql = "SELECT id "
                + "FROM product_classes "
                + "WHERE name = ?";

        try {
            pStmt = getConn().prepareStatement(sql);
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
     * 商品分類をデータベースに登録する。
     *  一意制約に違反した場合は-1を返す
     * @param name
     * @return
     */
    public int reg(String name) {
        PreparedStatement pStmt = null;
        int row = 0;

        String sql = "INSERT INTO product_classes(name) "
                + "VALUES(?)";

        try {
            pStmt = getConn().prepareStatement(sql);
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
