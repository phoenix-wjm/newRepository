package com.sfac.geniusdirecruit.modules.backstagesystem.pagecontroller;

import com.sfac.geniusdirecruit.modules.backstagesystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class LoginController {
    @Autowired
    private UserService userService;
    /**
     * http://127.0.0.1:8080/user/login ---- get
     */
    @RequestMapping("/login")
    public String loginPage(ModelMap modelMap) {
        modelMap.put("template", "backstagesystem/login");
        return "/common/managerIndexSimple";
    }

    /**
     * http://127.0.0.1:8080/user/register ---- get
     */
    @RequestMapping("/register")
    public String registerPage(ModelMap modelMap) {
        modelMap.put("template", "backstagesystem/register");
        return "/common/managerIndexSimple";
    }

    @RequestMapping("/index")
    public String index() {
        return "common/managerIndex";
    }

    @RequestMapping("/logout")
    public String logOut(ModelMap modelMap) {
        userService.logout();
        modelMap.addAttribute("template", "backstagesystem/login");
        return "/common/managerIndexSimple";
    }
}
