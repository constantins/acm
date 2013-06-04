package acm.controller;


import acm.model.Account;
import acm.model.User;
import acm.service.AccountService;
import acm.service.UserService;
import acm.vo.AccountVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    /**
     * list details for the curent account
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/accountDetails", method = RequestMethod.GET)
    public
    @ResponseBody
    AccountVO accountDetails(HttpServletRequest request, HttpServletResponse response) {
        String number = request.getSession().getAttribute("accountNo").toString();
        Account account = accountService.getAccountByNumber(number);
        AccountVO vo = new AccountVO(account);
        return vo;
    }

    /**
     * list all user acounts
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public
    @ResponseBody
    List<AccountVO> list(HttpServletRequest request, HttpServletResponse response) {
        Long userID = ((User) request.getSession().getAttribute("user")).getId();
        User u = userService.getUserByID(userID);

        List<Account> accounts = accountService.getAllAccountsByUser(u);
        List<AccountVO> result = new ArrayList<AccountVO>();
        for (Account account : accounts) {
            result.add(new AccountVO(account));
        }
        request.getSession().removeAttribute("accountNo");
        return result;
    }

    /**
     * modifies the user account according to the amount and the operation selected
     * @param request
     * @param response
     * @param operation
     * @param amount
     * @return
     */
    @RequestMapping(value = "/executeOperation", method = RequestMethod.POST)
    public
    @ResponseBody
    AccountVO add(HttpServletRequest request, HttpServletResponse response,
                  @RequestParam(value = "account_operation", required = true) String operation,
                  @RequestParam(value = "account_amount", required = true) String amount) {
        String number = request.getSession().getAttribute("accountNo").toString();

        Account account = accountService.update(number, operation, amount);
        AccountVO vo = new AccountVO(account);

        return vo;
    }

    /**
     * list details for the requested account
     * @param request
     * @param response
     * @param number
     * @return
     */
    @RequestMapping(value = "/accountList", method = RequestMethod.POST)
    public
    @ResponseBody
    AccountVO accountList(HttpServletRequest request, HttpServletResponse response,
                          @RequestParam(value = "accountnumber", required = true) String number) {
        Account account = accountService.getAccountByNumber(number);
        AccountVO vo = new AccountVO(account);
        request.getSession().setAttribute("accountNo", account.getNumber());

        return vo;
    }
}
