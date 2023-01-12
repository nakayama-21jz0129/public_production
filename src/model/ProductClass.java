package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import database.ProductClassDAO;
import database.ProductClassDTO;

public class ProductClass {
    private int id;
    private String name;

    /**
     * 引数無しコンストラクタ
     */
    public ProductClass() {
    }

    private ProductClass(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // getter・setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 全商品分類をProductClass型配列で返す。
     * @return
     */
    public ArrayList<ProductClass> getArray() {
        // return用
        ArrayList<ProductClass> array = new ArrayList<>();
        
        // DAO
        ProductClassDAO productClassDAO = new ProductClassDAO();
        
        // ProductClassDTO型配列をProductClass型配列に変換
        for (ProductClassDTO dto : productClassDAO.getArray()) {
            array.add(new ProductClass(
                    dto.getId(),
                    dto.getName()));
        }
        
        return array;
    }

    /**
     * 全商品分類をMap<Integer, String>型で返す。
     * @return
     */
    public Map<Integer, String> getMap() {
        // return用
        Map<Integer, String> map = new HashMap<>();
        
        // ProductClass型配列を連想配列に変換
        for (ProductClass element : getArray()) {
            element.toMap(map);
        }

        return map;
    }
    
    /**
     * 引数の連想配列にクラスのデータを追加する。
     * @param map
     */
    public void toMap(Map<Integer, String> map) {
        // フィールドの値をセット
        map.put(getId(), getName());
    }
}
