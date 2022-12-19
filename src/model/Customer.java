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
     * 
     * @return
     */
    public ArrayList<Customer> getCustomerList() {
        CustomerDAO customerDAO = new CustomerDAO();
        ArrayList<CustomerDTO> customerDTOList = null;
        ArrayList<Customer> customerList = new ArrayList<>();

        customerDTOList = customerDAO.getCustomerList();

        for (CustomerDTO dto : customerDTOList) {
            customerList.add(
                    new Customer(
                            dto.getId(),
                            dto.getTel(),
                            dto.getName(),
                            dto.getAddress(),
                            dto.getProductId1(),
                            dto.getProductId2()));
        }

        return customerList;
    }

    /**
     * 
     * @param tel
     * @param name
     * @param address
     * @return
     */
    public Map<String, Object> regCustomer(String tel, String name, String address) {
        CustomerDAO customerDAO = new CustomerDAO();
        Map<String, Object> map = new HashMap<>();
        int row;
        map.put("result", false);
        map.put("msg", "顧客の登録に失敗しました");

        if (tel != null && name != null && address != null) {
            if (tel.length() <= 15 && name.length() <= 64 && address.length() <= 192) {
                row = customerDAO.regCustomer(
                        tel,
                        name,
                        address);
                if (row == 1) {
                    map.replace("result", true);
                    map.replace("msg", "顧客の登録に成功しました");
                }
                else if (row == -1) {
                    map.replace("msg", "既に使用されている電話番号です");
                }
            }
        }

        return map;

    }

    /**
     * 
     * @return
     */
    public ArrayList<Map<String, Object>> getMapCustomerList() {
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        Product product = new Product();
        for (Customer element : getCustomerList()) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", element.getId());
            map.put("tel", element.getTel());
            map.put("name", element.getName());
            map.put("address", element.getAddress());
            map.put("product1", "");
            if (element.getProductId1() != 0) {
                product.setId(element.getProductId1());
                if (product.searchProduct()) {
                    map.replace("product1", product.getName());
                }
            }
            map.put("product2", "");
            if (element.getProductId2() != 0) {
                product.setId(element.getProductId2());
                if (product.searchProduct()) {
                    map.replace("product2", product.getName());
                }
            }
            
            list.add(map);
        }
        return list;
    }
    
    /**
     * 
     * @param id
     * @return
     */
    public Map<String, Object> delCustomer(int id) {
        CustomerDAO customerDAO = new CustomerDAO();
        Map<String, Object> map = new HashMap<>();
        int row;
        map.put("result", false);
        map.put("msg", "顧客の削除に失敗しました");
        
        row = customerDAO.delCustomer(id);
        if (row == 1) {
            map.replace("result", true);
            map.replace("msg", "顧客の削除に成功しました");
        }

        return map;
    }
    
    /**
     * 
     * @return
     */
    public boolean searchCustomer() {
        CustomerDAO customerDAO = new CustomerDAO();
        CustomerDTO customerDTO = null;

        if (getTel() != null) {
            if (getTel().length() <= 15) {
                customerDTO = customerDAO.searchCustomer(getTel());
                if (customerDTO != null) {
                    setId(customerDTO.getId());
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
     * 
     * @return
     */
    public Map<String, Object> getMapCustomer() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", getId());
        map.put("tel", getTel());
        map.put("name", getName());
        map.put("address", getAddress());
        
        Product product = new Product();
        
        map.put("product1", "");
        if (getProductId1() != 0) {
            product.setId(getProductId1());
            if (product.searchProduct()) {
                map.replace("product1", product.getName());
            }
        }
        
        map.put("product2", "");
        if (getProductId2() != 0) {
            product.setId(getProductId2());
            if (product.searchProduct()) {
                map.replace("product2", product.getName());
            }
        }
        
        return map;
    }
}
