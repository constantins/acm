package acm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("list")
public class ListController {

    @RequestMapping
    public String showHomePage() {
        return "list";
    }
}
