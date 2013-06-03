package acm.service.impl;

import acm.dao.AccountDAO;
import acm.dao.UserDAO;
import acm.model.Account;
import acm.model.User;
import acm.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private AccountDAO accountDAO;

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Override
    public Account update(String number, String operation, String amount) {
        Account existentAccount = accountDAO.getAccountByNumber(number);
        if ("deposit".equalsIgnoreCase(operation)) {
            existentAccount.setSold(existentAccount.getSold() + Long.parseLong(amount));
        } else {
            existentAccount.setSold(existentAccount.getSold() - Long.parseLong(amount));
        }

        Account account = accountDAO.update(existentAccount);
        return account;
    }

    @Override
    public List<Account> getAllAccountsByUser(User u) {
        return accountDAO.getAllAccountsByUser(u);
    }

    @Override
    public Account getAccountByNumber(String number) {
        return accountDAO.getAccountByNumber(number);
    }
}
