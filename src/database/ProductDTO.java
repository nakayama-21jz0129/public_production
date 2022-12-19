package database;

public class ProductDTO {
    private int id;
    private String name;
    private int price;
    private int productClassId;
    private boolean useFlag;

    
    public ProductDTO(int id, String name, int price, int productClassId, int useFlag) {
        setId(id);
        setName(name);
        setPrice(price);
        setProductClassId(productClassId);
        setUseFlag(useFlag == 1);
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

}
