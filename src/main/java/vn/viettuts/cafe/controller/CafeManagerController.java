package vn.viettuts.cafe.controller;


import java.util.ArrayList;
import java.util.List;

import vn.viettuts.cafe.entity.Order;
import vn.viettuts.cafe.entity.Product;
import vn.viettuts.cafe.entity.ProductXML;
import vn.viettuts.cafe.utils.FileUtils;

public final class CafeManagerController {
    private static final String PRODUCT_FILE_NAME = "product.xml";
    private List<Product> listProducts;
    private final List<Order> orders;

    public CafeManagerController() {
        this.listProducts = readListProducts();
        if (listProducts == null) {
            listProducts = new ArrayList<Product>();
        }
        orders = new ArrayList<>();
    }
    
    public void writeListProducts(List<Product> products) {
        ProductXML productXML = new ProductXML();
        productXML.setProduct(products);
        FileUtils.writeXMLtoFile(PRODUCT_FILE_NAME, productXML);
    }

    public List<Product> readListProducts() {
        List<Product> list = new ArrayList<Product>();
        ProductXML productXML = (ProductXML) FileUtils.readXMLFile(PRODUCT_FILE_NAME, ProductXML.class);
        if (productXML != null) {
            list = productXML.getProduct();
            
        }
        return list;
    }

    public List<Product> getProducts() {
        return listProducts;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void addOrder(Product product, int quantity) {
        Order order = new Order(product, quantity);
        orders.add(order);
    }

    public double calculateTotal() {
        double total = 0.0;
        for (Order order : orders) {
            total += order.getTotal();
        }
        return total;
    }
    public List<Product> search(String keyword) {
        List<Product> results = new ArrayList<>();

        for (Product product : listProducts) {
            if (product.getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(product);
            }
        }

        return results;
    }
    public List<Product> searchByPrice(double price) {
    List<Product> results = new ArrayList<>();

    for (Product product : listProducts) {
        double productPrice = product.getPrice();
        if (productPrice == price) {
            results.add(product);
        }
    }

        return results;
    }

    public void clearOrders() {
        orders.clear();
    }
}