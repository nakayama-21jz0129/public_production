package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import database.CustomerDAO;
import database.CustomerDTO;
import database.ProductClassDAO;
import database.ProductDAO;
import database.ProductDTO;

public class Product {
    private int id;
    private String name;
    private int price;
    private int productClassId;
    private boolean useFlag;

    public Product() {
    }

    private Product(int id, String name, int price, int productClassId, boolean useFlag) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productClassId = productClassId;
        this.useFlag = useFlag;
    }

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
     * 
     * @return
     */
    public ArrayList<Product> getProductList(boolean bool) {
        ProductDAO productDAO = new ProductDAO();
        ArrayList<ProductDTO> productDTOList = null;
        ArrayList<Product> productList = new ArrayList<>();

        productDTOList = productDAO.getProductList(bool);

        for (ProductDTO dto : productDTOList) {
            productList.add(
                    new Product(
                            dto.getId(),
                            dto.getName(),
                            dto.getPrice(),
                            dto.getProductClassId(),
                            dto.isUseFlag()));
        }

        return productList;
    }
    
    /**
     * 
     * @return
     */
    public Map<Integer, Object> getMapProductList(boolean bool) {
        Map<Integer, Object> returnMap = new HashMap<>();
        for (Product element: getProductList(bool)) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", element.getName());
            map.put("price", element.getPrice());
            map.put("product_class_id", element.getProductClassId());
            map.put("use_flag", element.isUseFlag());
            returnMap.put(element.getId(), map);
        }
        return returnMap;
    }
    
    /**
     * 
     * @return
     */
    public Map<Integer, Map> getProductMap(boolean bool) {
        ProductClass productClass = new ProductClass();
        Map<Integer, Map> productMap = new HashMap<>();
        Map<Integer, String> productClassMap = productClass.getProductClassMap();
        Map<Integer, Object> mapProduct = getMapProductList(bool);
        
        for (Integer productId : mapProduct.keySet()) {
            Map<String, Object> product = (Map<String, Object>)mapProduct.get(productId);
            if (!productMap.containsKey(product.get("product_class_id"))) {
                Map<String, Object> newMap = new HashMap<>();
                newMap.put("name", productClassMap.get(product.get("product_class_id")));
                newMap.put("product", new HashMap<Integer, Object>());
                productMap.put((Integer)product.get("product_class_id"), newMap);
            }
            Map<Integer, Object> pMap = (Map<Integer, Object>)productMap.get(product.get("product_class_id")).get("product");
            pMap.put(productId, mapProduct.get(productId));
        }
        
        return productMap;
    }

    /**
     * 
     * @param productClassName
     * @param name
     * @param price
     * @param useFlag
     * @return
     */
    public Map<String, Object> regProduct(String productClassName, String name, int price, boolean useFlag) {
        ProductDAO productDAO = new ProductDAO();
        ProductClassDAO productClassDAO = new ProductClassDAO();
        Map<String, Object> map = new HashMap<>();
        int product_class_id = 0;
        int row;
        map.put("result", false);
        map.put("msg", "商品の登録に失敗しました");
        
        if (productClassName != null && name != null) {
            if (productClassName.length() <= 64 && name.length() <= 64) {
                product_class_id = productClassDAO.searchProductClass(productClassName);
                
                if (product_class_id == -1) {
                    if (productClassDAO.regProductClass(productClassName) == 1) {
                        product_class_id = productClassDAO.searchProductClass(productClassName);
                    }
                    else {
                        map.replace("msg", "新しいカテゴリの登録に失敗しました");
                        
                        return map;
                    }
                }
                row = productDAO.regProduct(
                        name,
                        price,
                        product_class_id, 
                        useFlag ? 1 : 0);
                if (row == 1) {
                    map.replace("result", true);
                    map.replace("msg", "商品の登録に成功しました");
                }
                else if (row == -1) {
                    map.replace("msg", "既に同じカテゴリに存在する商品名は使用できません");
                }
            }
        }

        return map;
    }
    
    /**
     * 
     * @param id
     * @return
     */
    public Map<String, Object> delProduct(int id) {
        ProductDAO productDAO = new ProductDAO();
        Map<String, Object> map = new HashMap<>();
        int row;
        map.put("result", false);
        map.put("msg", "商品の削除に失敗しました");
        
        row = productDAO.delProduct(id);
        if (row == 1) {
            map.replace("result", true);
            map.replace("msg", "商品の削除に成功しました");
        }

        return map;
    }
    
    /**
     * 
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
}
