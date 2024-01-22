package vn.viettuts.cafe.controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;

import vn.viettuts.cafe.entity.Order;
import vn.viettuts.cafe.entity.Product;
import vn.viettuts.cafe.entity.ProductXML;
import vn.viettuts.cafe.utils.FileUtils;
import vn.viettuts.cafe.view.CafeManagerView;

public final class ProductManagerController {
    private static final String PRODUCT_FILE_NAME = "product.xml";
    private List<Product> listProducts;
    private final List<Order> orders;

    public ProductManagerController() {
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
    
    public void addProduct(String name, double price) {
        Product newProduct = new Product(name, price);
        listProducts.add(newProduct);
        writeListProducts(listProducts);
    }

    public List<Product> getProducts() {
        return listProducts;
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

    public void removeProduct(String name) {
        listProducts.removeIf(product -> product.getName().equalsIgnoreCase(name));
        writeListProducts(listProducts);
    }
    
    public void clearOrders() {
        orders.clear();
    }
}