package acm.service.impl;

import acm.dao.AccountDAO;
import acm.dao.UserDAO;
import acm.model.User;
import acm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private AccountDAO accountDAO;

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Autowired
    private UserDAO userDAO;

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public User getUserByName(String username) {
        return userDAO.getUserByName(username);
    }

    @Override
    public User getUserByID(Long userid) {
        return userDAO.getUserById(userid);
    }
}
