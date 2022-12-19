package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import database.ProductClassDAO;
import database.ProductClassDTO;

public class ProductClass {
    private int id;
    private String name;

    public ProductClass() {
    }

    public ProductClass(int id, String name) {
        this.id = id;
        this.name = name;
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

    public Map<Integer, String> getProductClassMap() {
        ProductClassDAO productClassDAO = new ProductClassDAO();
        ArrayList<ProductClassDTO> productClassDTOList = null;
        Map<Integer, String> productClassList = new HashMap<>();

        productClassDTOList = productClassDAO.getProductClassList();

        for (ProductClassDTO dto : productClassDTOList) {
            productClassList.put(dto.getId(), dto.getName());
        }

        return productClassList;
    }
}
