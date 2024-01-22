package vn.viettuts.cafe.dao;

import vn.viettuts.cafe.entity.User;

public class UserDao {
    public boolean checkUser(User user) {
        if (user != null) {
            if ("a".equals(user.getUserName()) && "a".equals(user.getPassword())) {
                return true;
            }
        }
        return false;
    }
}
