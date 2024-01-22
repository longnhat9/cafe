package vn.viettuts.cafe.view;

import vn.viettuts.cafe.controller.CafeManagerController;
import vn.viettuts.cafe.controller.ProductManagerController;
import vn.viettuts.cafe.entity.Order;
import vn.viettuts.cafe.entity.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class CafeManagerView extends JFrame {
    private final CafeManagerController cafeManager;
    private ProductManagerController productManager;
    private DefaultListModel<String> productListModel;
    private JList<String> productList;
    private JTextField quantityField;
    private JTextArea orderTextArea;

    public CafeManagerView() {
        cafeManager = new CafeManagerController();
        setTitle("Quản lý quán cafe");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initUI();
    }

    private void clearOrder() {
        cafeManager.clearOrders();
        
        updateOrderTextArea();
        quantityField.setText("");
        updateProductListOrder((new ProductManagerController()).readListProducts());
    }

    private void initUI() {
        JPanel panel = new JPanel(new BorderLayout());

        productListModel = new DefaultListModel<>();
        for (Product product : cafeManager.getProducts()) {
            productListModel.addElement(product.getName() + " - " + product.getPrice() + " VND");
        }
        productList = new JList<>(productListModel);
        JScrollPane productScrollPane = new JScrollPane(productList);
        panel.add(productScrollPane, BorderLayout.CENTER);

        JPanel orderPanel = new JPanel(new FlowLayout());
        JLabel quantityLabel = new JLabel("Số lượng:");
        quantityField = new JTextField(5);
        JButton orderButton = new JButton("Đặt món");
        orderButton.addActionListener((ActionEvent e) -> addToOrder());
        orderPanel.add(quantityLabel);
        orderPanel.add(quantityField);
        orderPanel.add(orderButton);
        panel.add(orderPanel, BorderLayout.SOUTH);

        orderTextArea = new JTextArea(10, 20);
        orderTextArea.setEditable(false);
        JScrollPane orderScrollPane = new JScrollPane(orderTextArea);
        panel.add(orderScrollPane, BorderLayout.EAST);

        JPanel totalPanel = new JPanel(new FlowLayout());
        JButton calculateTotalButton = new JButton("Thành tiền");
        calculateTotalButton.addActionListener((ActionEvent e) -> calculateTotal());
        JButton clearOrderButton = new JButton("Thanh toán");
        clearOrderButton.addActionListener((ActionEvent e) -> clearOrder());
        JButton reloadButton = new JButton("Làm mới");
        reloadButton.addActionListener((ActionEvent e) -> clearOrder());
        totalPanel.add(calculateTotalButton);
        totalPanel.add(clearOrderButton);
        totalPanel.add(reloadButton);
        panel.add(totalPanel, BorderLayout.WEST);

        JPanel searchPanel = new JPanel(new FlowLayout());
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Tìm kiếm");
        
        JPanel productManagerPanel = new JPanel(new FlowLayout());
        JTextField productManagerField = new JTextField(15);
        JButton productManagerButton = new JButton("Quản lý sản phẩm");

        // Xử lý sự kiện khi kích vào nút tìm kiếm
        searchButton.addActionListener((ActionEvent e) -> {
            String keyword = searchField.getText();
            performSearch(keyword);
        });
        // Xử lý sự kiện khi kích vào nút tìm kiếm
        productManagerButton.addActionListener((ActionEvent e) -> {
            String keyword = searchField.getText();
            performProductManager(keyword);
        });

        // Thêm các thành phần vào panel
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(productManagerButton);
        // Thêm panel tìm kiếm vào trang chính
        panel.add(searchPanel, BorderLayout.NORTH);

        // Thêm panel chính vào JFrame
        add(panel);
    }

    private void performSearch(String keyword) {
        if (isNumeric(keyword)) {
            performNumericSearch(Double.parseDouble(keyword));
        } else {
            List<Product> searchResults = cafeManager.search(keyword);
            displaySearchResults(searchResults);
        }
    }
    
    private void performProductManager(String keyword) {
        if (isNumeric(keyword)) {
            performNumericSearch(Double.parseDouble(keyword));
        } else {
            ProductManagerView productManagerView = new ProductManagerView();
            productManagerView.setVisible(true);
        }
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void performNumericSearch(double price) {
        List<Product> searchResults = cafeManager.searchByPrice(price);
        displaySearchResults(searchResults);
    }

    private void displaySearchResults(List<Product> searchResults) {
        productListModel.clear();

        if (searchResults.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sản phẩm nào.", "Kết quả tìm kiếm", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Product product : searchResults) {
                productListModel.addElement(product.getName() + " - " + product.getPrice() + " VND");
            }
        }
    }

    private void addToOrder() {
        int selectedIndex = productList.getSelectedIndex();
        if (selectedIndex != -1) {
            Product selectedProduct = cafeManager.getProducts().get(selectedIndex);
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                cafeManager.addOrder(selectedProduct, quantity);
                updateOrderTextArea();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ký tự số nhập chưa đúng. Hãy nhập ký tự số", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Hãy chọn món trong menu.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateOrderTextArea() {
        StringBuilder orderText = new StringBuilder();
        for (Order order : cafeManager.getOrders()) {
            orderText.append(order.getProduct().getName()).append(" x ").append(order.getQuantity()).append("\n");
        }
        orderTextArea.setText(orderText.toString());
    }
    
    public void updateProductListOrder(List<Product> productList){
        productListModel.clear();
        
        for (Product product : productList) {
            productListModel.addElement(product.getName() + " - " + product.getPrice() + " VND");
        }
    }

    private void calculateTotal() {
        double total = cafeManager.calculateTotal();
        JOptionPane.showMessageDialog(this, "Tổng tiền: " + total + " VND", "Total", JOptionPane.INFORMATION_MESSAGE);
    }
}
