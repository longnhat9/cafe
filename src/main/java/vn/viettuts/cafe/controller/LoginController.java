package vn.viettuts.cafe.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import vn.viettuts.cafe.dao.UserDao;
import vn.viettuts.cafe.entity.User;
import vn.viettuts.cafe.view.LoginView;
import vn.viettuts.cafe.view.CafeManagerView;

public class LoginController {
    private UserDao userDao;
    private LoginView loginView;
    private CafeManagerView cafeManagerView;
    
    public LoginController(LoginView view) {
        this.loginView = view;
        this.userDao = new UserDao();
        view.addLoginListener(new LoginListener());
    }
    
    public void showLoginView() {
        loginView.setVisible(true);
    }

    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            User user = loginView.getUser();
            if (userDao.checkUser(user)) {
                cafeManagerView = new CafeManagerView();
                cafeManagerView.setVisible(true);
                loginView.setVisible(false);
            } else {
                loginView.showMessage("username hoặc password không đúng.");
            }
        }
    }
}
