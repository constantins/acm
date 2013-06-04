package acm.dao;

import acm.model.Account;
import acm.model.User;
import acm.service.AccountService;
import acm.service.UserService;
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
public class AccountDAOTest {

    private final String accountNo1 = "accountno1";
    private final String accountNo2 = "accountno2";
    private final Long amount1 = 1L;
    private final String u1Name = "user1";

    @Autowired
    private UserDAO userDAO;

    public UserDAO getUserDAO() {
        return userDAO;
    }

    @Autowired
    private AccountDAO accountDAO;

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Test
    public void testGetAccountByNumber() {
        Account acc1 = accountDAO.getAccountByNumber(accountNo1);
        assertNotNull(acc1.getId());

        Account acc2 = accountDAO.getAccountByNumber(accountNo2);
        assertNotNull(acc2.getId());
    }

    @Test
    public void testUpdate() {
        Account acc1 = accountDAO.getAccountByNumber(accountNo1);
        assertNotNull(acc1.getId());
        Long sold = acc1.getSold();

        acc1.setSold(amount1);
        Account acc_op1 = accountDAO.update(acc1);
        assertNotNull(acc_op1);
        assertTrue(acc_op1.getSold().longValue() == amount1);


        acc_op1.setSold(sold);
        Account acc_op2 = accountDAO.update(acc_op1);
        assertNotNull(acc_op2);
        assertTrue(acc_op2.getSold().longValue() == sold.longValue());
    }

    @Test
    public void testGetAllAccountsByUser() {
        User u = userDAO.getUserByName(u1Name);
        assertNotNull(u.getId());

        List<Account> accounts = accountDAO.getAllAccountsByUser(u);
        assertNotNull(accounts);
        assertTrue(accounts.size() > 0);
    }
}
