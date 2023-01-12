package model;

public class OrderDetail {
    private int productId;
    private int quantity;

    /**
     * 引数ありコンストラクタ
     * 
     * @param productId
     * @param quantity
     */
    public OrderDetail(int productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    // getter・setter
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
