package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import database.CustomerDAO;
import database.CustomerDTO;

public class Customer {
    private int id;
    private String tel;
    private String name;
    private String address;
    private int productId1;
    private int productId2;

    /**
     * 引数無しコンストラクタ
     */
    public Customer() {
    }

    private Customer(int id, String tel, String name, String address, int productId1, int productId2) {
        this.id = id;
        this.tel = tel;
        this.name = name;
        this.address = address;
        this.productId1 = productId1;
        this.productId2 = productId2;
    }
    // getter・setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getProductId1() {
        return productId1;
    }

    public void setProductId1(int productId1) {
        this.productId1 = productId1;
    }

    public int getProductId2() {
        return productId2;
    }

    public void setProductId2(int productId2) {
        this.productId2 = productId2;
    }

    /**
     * 全顧客をCustomer型配列で返す。
     * @return
     */
    public ArrayList<Customer> getArray() {
        // return用
        ArrayList<Customer> array = new ArrayList<>();
        
        // DAO・DTO
        CustomerDAO customerDAO = new CustomerDAO();
        ArrayList<CustomerDTO> customerDTOArray = null;
        
        // CustomerDTO型配列をCustomer型配列に変換
        customerDTOArray = customerDAO.getArray();
        for (CustomerDTO dto : customerDTOArray) {
            array.add(
                    new Customer(
                            dto.getId(),
                            dto.getTel(),
                            dto.getName(),
                            dto.getAddress(),
                            dto.getProductId1(),
                            dto.getProductId2()));
        }

        return array;
    }

    /**
     * 顧客の登録処理をする。
     * @param tel
     * @param name
     * @param address
     * @return
     */
    public int reg(String tel, String name, String address) {
        // return用
        int result = 0;
        
        // DAO
        CustomerDAO customerDAO = new CustomerDAO();

        // 引数のnullを確認
        if (tel != null && name != null && address != null) {
            
            // 引数の文字数を確認
            if (tel.length() <= 15 && name.length() <= 64 && address.length() <= 192) {
                result = customerDAO.reg(
                        tel,
                        name,
                        address);
            }
        }

        return result;

    }

    /**
     * 全顧客を連想配列型配列で返す。
     * @deprecated
     * @see getMap
     * @return
     */
    public ArrayList<Map<String, Object>> getMapArray() {
        // return用
        ArrayList<Map<String, Object>> array = new ArrayList<>();
        
        // Customer型配列をMap<String, Object>型配列に変換
        for (Customer element : getArray()) {
            array.add(element.toMap());
        }
        
        return array;
    }
    
    /**
     * 顧客の削除処理をする。
     * @param id
     * @return
     */
    public boolean del(int id) {
        
        // DAO
        CustomerDAO customerDAO = new CustomerDAO();
        
        // 顧客を削除
        if (customerDAO.del(id) == 1) {
            return true;
        }

        return false;
    }
    
    /**
     * 電話番号から顧客を探す。
     * @return
     */
    public boolean search(String tel) {
        // DAO・DTO
        CustomerDAO customerDAO = new CustomerDAO();
        CustomerDTO customerDTO = null;

        // 電話番号を確認
        if (tel != null) {
            
            // 電話番号の文字数を確認
            if (tel.length() <= 15) {
                
                // 顧客を探す
                customerDTO = customerDAO.search(tel);
                if (customerDTO != null) {
                    // フィールドに値をセット
                    setId(customerDTO.getId());
                    setTel(customerDTO.getTel());
                    setName(customerDTO.getName());
                    setAddress(customerDTO.getAddress());
                    setProductId1(customerDTO.getProductId1());
                    setProductId2(customerDTO.getProductId2());
                    
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 識別子から顧客を探す。
     * @param id
     * @return
     */
    public boolean search(int id) {
        // DAO・DTO
        CustomerDAO customerDAO = new CustomerDAO();
        CustomerDTO customerDTO = null;

        // 識別子を確認
        if (id != 0) {
                
            // 顧客を探す
            customerDTO = customerDAO.search(id);
            if (customerDTO != null) {
                
                // フィールドに値をセット
                setId(customerDTO.getId());
                setTel(customerDTO.getTel());
                setName(customerDTO.getName());
                setAddress(customerDTO.getAddress());
                setProductId1(customerDTO.getProductId1());
                setProductId2(customerDTO.getProductId2());
                
                return true;
            }
        }
        return false;
    }
    
    /**
     * クラスのデータを連想配列で返す。
     * @return
     */
    public Map<String, Object> toMap() {
        // return用
        Map<String, Object> map = new HashMap<>();
        
        // フィールドの値をセット
        map.put("id", getId());
        map.put("tel", getTel());
        map.put("name", getName());
        map.put("address", getAddress());
        
        return map;
    }
    
    /**
     * クラスのデータを連想配列で返す。
     *  商品名を含む
     * @param productMap
     * @return
     */
    public Map<String, Object> toMapExt() {
        // return用
        Map<String, Object> map = toMap();
        
        // インスタンスを作成
        Product product = new Product();
        
        // 全商品を取得
        Map<Integer, Map> productMap = product.getMap(true);
        
        // 対象商品名のセット
        map.put("product1", "");
        if (getProductId1() != 0) {
            if (productMap.containsKey(getProductId1())) {
                map.replace("product1", productMap.get(getProductId1()).get("name"));
            }
        }
        
        // 対象商品名のセット
        map.put("product2", "");
        if (getProductId2() != 0) {
            if (productMap.containsKey(getProductId2())) {
                map.replace("product2", productMap.get(getProductId2()).get("name"));
            }
        }
        
        return map;
    }
}
