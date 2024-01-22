package vn.viettuts.cafe;

import java.awt.EventQueue;

import vn.viettuts.cafe.controller.LoginController;
import vn.viettuts.cafe.view.CafeManagerView;
import vn.viettuts.cafe.view.LoginView;

public class App {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                CafeManagerView cafeManagerView = new CafeManagerView();
                cafeManagerView.setVisible(true);
//                LoginView view = new LoginView();
//                LoginController controller = new LoginController(view);
                // hiển thị màn hình login
//                controller.showLoginView();
            }
        });
    }
}