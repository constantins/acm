package acm.service;

import acm.model.Account;
import acm.model.User;

import java.util.List;

public interface AccountService {

    Account update(String number, String operation, String amount);

    List<Account> getAllAccountsByUser(User u);

    Account getAccountByNumber(String number);

}
