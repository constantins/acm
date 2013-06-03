package acm.dao;

import acm.model.Account;
import acm.model.User;

import java.util.List;

public interface AccountDAO {

    Account update(Account existingAccount);

    List<Account> getAllAccountsByUser(User u);

    Account getAccountByNumber(String number);

}
