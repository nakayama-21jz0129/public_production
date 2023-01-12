package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import conversion.Format;
import database.ProductClassDAO;
import database.ProductDAO;
import database.ProductDTO;

public class Product {
    private int id;
    private String name;
    private int price;
    private int productClassId;
    private boolean useFlag;

    /**
     * 引数無しコンストラクタ
     */
    public Product() {
    }
    
    private Product(int id, String name, int price, int productClassId, boolean useFlag) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productClassId = productClassId;
        this.useFlag = useFlag;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getProductClassId() {
        return productClassId;
    }

    public void setProductClassId(int productClassId) {
        this.productClassId = productClassId;
    }

    public boolean isUseFlag() {
        return useFlag;
    }

    public void setUseFlag(boolean useFlag) {
        this.useFlag = useFlag;
    }

    /**
     * 全商品をProduct型配列で返す。
     * @param bool
     * @return
     */
    public ArrayList<Product> getArray(boolean bool) {
        // return用
        ArrayList<Product> productArray = new ArrayList<>();
        
        // DAO
        ProductDAO productDAO = new ProductDAO();
        
        // ProductDTO型配列をProduct型配列に変換
        for (ProductDTO dto : productDAO.getArray(bool)) {
            productArray.add(
                    new Product(
                            dto.getId(),
                            dto.getName(),
                            dto.getPrice(),
                            dto.getProductClassId(),
                            dto.isUseFlag()));
        }

        return productArray;
    }
    
    /**
     * 全商品を連想配列で返す。
     * @param bool
     * @return
     */
    public Map<Integer, Map> getMap(boolean bool) {
        // return用
        Map<Integer, Map> map = new HashMap<>();
        
        // Product型配列をMap<Integer, Map>型配列に変換
        for (Product element: getArray(bool)) {
            element.toMap(map);
        }
        
        return map;
    }
    
    /**
     * 商品分類で分けられた全商品を連結で返す。
     * @param bool
     * @return
     */
    public Map<Integer, Map> getMapExt(boolean bool) {
        // return用
        Map<Integer, Map> map = new HashMap<>();
        
        // インスタンスを作成
        ProductClass productClass = new ProductClass();
        
        // 全商品を取得
        Map<Integer, Map> productMap = getMap(bool);
        
        // 全商品分類を取得
        Map<Integer, String> productClassMap = productClass.getMap();
        
        // 連想配列のキーでループ
        for (Integer productMapKey : productMap.keySet()) {
            
            // キーから値を取得
            Map<String, Object> productMapValue = productMap.get(productMapKey);
            
            // 商品分類の存在を確認
            if (!map.containsKey(productMapValue.get("product_class_id"))) {
                
                // 商品分類をセット
                Map<String, Object> innerMap = new HashMap<>();
                innerMap.put("name", productClassMap.get(productMapValue.get("product_class_id")));
                innerMap.put("product", new HashMap<Integer, Map>());
                map.put((Integer)productMapValue.get("product_class_id"), innerMap);
            }
            
            // 商品をセット
            ((Map<Integer, Map>)(map.get(productMapValue.get("product_class_id"))
                    .get("product"))).put(productMapKey, productMapValue);
        }
        
        return map;
    }

    /**
     * 商品の登録処理をする。
     * @param productClassName
     * @param name
     * @param price
     * @param useFlag
     * @return
     */
    public int reg(String productClassName, String name, int price, boolean useFlag) {
        // return用
        int result = 0;
        
        // DAO
        ProductDAO productDAO = new ProductDAO();
        ProductClassDAO productClassDAO = new ProductClassDAO();
        
        // 引数のnullを確認
        if (productClassName != null && name != null) {
            
            // 引数の文字数を確認
            if (productClassName.length() <= 32 && name.length() <= 32) {
                
                // 商品分類名から商品分類識別子を探す
                int productClassId = productClassDAO.search(productClassName);
                
                // 商品分類が未設定の場合
                if (productClassId == -1) {
                    
                    // 商品分類を登録
                    if (productClassDAO.reg(productClassName) == 1) {
                        
                        // 商品分類名から商品分類識別子を探す
                        productClassId = productClassDAO.search(productClassName);
                    }
                    else {
                        result = -2;
                        return result;
                    }
                }
                
                // 商品を登録
                result = productDAO.reg(
                        name,
                        price,
                        productClassId, 
                        Format.booleanToInt(useFlag));
            }
        }

        return result;
    }
    
    /**
     * 商品の削除処理をする。
     * @param id
     * @return
     */
    public boolean del(int id) {
        
        // DAO
        ProductDAO productDAO = new ProductDAO();
        
        // 商品を削除
        if (Format.intToBoolean(productDAO.del(id))) {
            return true;
        }

        return false;
    }
    
    /**
     * 使ってる？
     * 
     * @deprecated
     * @return
     */
    public boolean searchProduct() {
        ProductDAO productDAO = new ProductDAO();
        ProductDTO productDTO = null;

        if (getId() != 0) {
            productDTO = productDAO.searchProduct(getId());
            if (productDTO != null) {
                setId(productDTO.getId());
                setName(productDTO.getName());
                setPrice(productDTO.getPrice());
                setProductClassId(productDTO.getProductClassId());
                setUseFlag(productDTO.isUseFlag());
                
                if (isUseFlag()) {
                   return true; 
                }
            }
        }
        return false;
    }
    
    /**
     * 引数の連想配列にクラスのデータを追加する。
     * @param outMap
     */
    public void toMap(Map<Integer, Map> outMap) {
        // セット用
        Map<String, Object> map = new HashMap<>();
        
        // フィールドの値をセット
        map.put("name", getName());
        map.put("price", getPrice());
        map.put("product_class_id", getProductClassId());
        map.put("use_flag", isUseFlag());
        
        outMap.put(getId(), map);
    }
}
