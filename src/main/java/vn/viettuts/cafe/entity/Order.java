package vn.viettuts.cafe.entity;

public class Order {
    private Product product;
    private int quantity;
    
    public Order(){}
    
    public Order(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotal() {
        return product.getPrice() * quantity;
    }
}