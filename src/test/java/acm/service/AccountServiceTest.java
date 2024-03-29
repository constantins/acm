package acm.service;

import acm.model.Account;
import acm.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/application-context.xml"})

public class AccountServiceTest {

    private final String deposit = "deposit";
    private final String withdraw = "withdraw";
    private final String accountNo1 = "accountno1";
    private final String accountNo2 = "accountno2";
    private final String amount1 = "1";
    private final String u1Name = "user1";

    @Autowired
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private AccountService accountService;

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @Test
    public void testGetAccountByNumber() {
        Account acc1 = accountService.getAccountByNumber(accountNo1);
        assertNotNull(acc1.getId());

        Account acc2 = accountService.getAccountByNumber(accountNo2);
        assertNotNull(acc2.getId());
    }

    @Test
    public void testUpdate() {
        Account acc1 = accountService.getAccountByNumber(accountNo1);
        assertNotNull(acc1.getId());

        Account acc_op1 = accountService.update(acc1.getNumber(), deposit, amount1);
        assertNotNull(acc_op1);
        assertTrue(acc1.getSold() + 1 == acc_op1.getSold());


        Account acc_op2 = accountService.update(acc1.getNumber(), withdraw, amount1);
        assertNotNull(acc_op2);
        assertTrue(acc1.getSold().longValue() == acc_op2.getSold().longValue());
    }

    @Test
    public void testGetAllAccountsByUser() {
        User u = userService.getUserByName(u1Name);
        assertNotNull(u.getId());

        List<Account> accounts = accountService.getAllAccountsByUser(u);
        assertNotNull(accounts);
        assertTrue(accounts.size() > 0);
    }

}
