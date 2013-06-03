package acm.controller;

import acm.model.User;
import acm.service.UserService;
import acm.vo.CommonRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/isLogin", method = RequestMethod.GET)
    public
    @ResponseBody
    CommonRequestStatus isLogin(HttpServletRequest request, HttpServletResponse response) {
        User u = (User) request.getSession().getAttribute("user");
        CommonRequestStatus status = new CommonRequestStatus();

        if (u != null) {
            status.setStatus("true");
            status.setMessage(u.getUsername());
        } else {
            status.setStatus("false");
        }

        return status;
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public
    @ResponseBody
    User getUser(HttpServletRequest request, HttpServletResponse response,
                 @RequestParam(value = "username", required = true) String username) {
        User u = userService.getUserByName(username);
        return u;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();

        return "index";
    }

    @RequestMapping(value = {"/checkLogin", "/login"}, method = RequestMethod.POST)
    public
    @ResponseBody
    CommonRequestStatus checkLogin(HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam(value = "userid", required = true) String userid) {
        User storedUser = userService.getUserByID(new Long(userid));

        CommonRequestStatus status = new CommonRequestStatus();
        if (storedUser != null && storedUser.getUsername() != null) {
            status.setStatus("true");
            if (request.getSession().getAttribute("user") == null) {
                request.getSession().setAttribute("user", storedUser);
            }
        } else {
            status.setStatus("false");
        }
        return status;
    }
}
