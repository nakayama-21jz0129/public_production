package database;

public class CustomerDTO {
    private int id;
    private String tel;
    private String name;
    private String address;
    private int productId1;
    private int productId2;

    public CustomerDTO(int id, String tel, String name, String address, int productId1, int productId2) {
        setId(id);
        setTel(tel);
        setName(name);
        setAddress(address);
        setProductId1(productId1);
        setProductId2(productId2);
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

   
    

}
