package vn.viettuts.cafe.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import vn.viettuts.cafe.controller.ProductManagerController;
import vn.viettuts.cafe.entity.Product;

public class ProductManagerView extends JFrame {
    private final ProductManagerController productManager;
    private DefaultListModel<String> productListModel;
    private JList<String> productList;
    private JTextField productNameField;
    private JTextField productPriceField;

    public ProductManagerView() {
        productManager = new ProductManagerController();
        setTitle("Quản lý sản phẩm");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout());

        productListModel = new DefaultListModel<>();
        productList = new JList<>(productListModel);
        JScrollPane productScrollPane = new JScrollPane(productList);
        panel.add(productScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        JButton addProductButton = new JButton("Thêm sản phẩm");
        JButton removeProductButton = new JButton("Xóa sản phẩm");

        addProductButton.addActionListener((ActionEvent e) -> {
            addProduct();
        });

        removeProductButton.addActionListener((ActionEvent e) -> {
            removeProduct();
        });

        buttonPanel.add(addProductButton);
        buttonPanel.add(removeProductButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        productNameField = new JTextField();
        productPriceField = new JTextField();
        inputPanel.add(new JLabel("Tên sản phẩm:"));
        inputPanel.add(productNameField);
        inputPanel.add(new JLabel("Giá sản phẩm:"));
        inputPanel.add(productPriceField);
        panel.add(inputPanel, BorderLayout.NORTH);

        add(panel);

        // Initialize product list
        updateProductList();
    }

    private void addProduct() {
        String productName = productNameField.getText();
        String productPriceText = productPriceField.getText();

        if (!productName.isEmpty() && !productPriceText.isEmpty()) {
            try {
                double productPrice = Double.parseDouble(productPriceText);
                productManager.addProduct(productName, productPrice);

                // Update the product list
                updateProductList();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Nhập giá sản phẩm chưa hợp lệ. Hãy nhập lại giá sản phẩm.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Hãy nhập tên sản phẩm và giá sản phẩm.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeProduct() {
        String selectedProduct = productList.getSelectedValue();

        if (selectedProduct != null) {
            // Extract product name from the selected item
            String productName = selectedProduct.split(" - ")[0];

            productManager.removeProduct(productName);

            // Update the product list
            updateProductList();
        } else {
            JOptionPane.showMessageDialog(this, "Hãy lựa chọn sản phẩm để xóa.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateProductList() {
        productListModel.clear();
        List<Product> products = productManager.getProducts();
        for (Product product : products) {
            productListModel.addElement(product.getName() + " - " + product.getPrice() + " VND");
        }
    }
}