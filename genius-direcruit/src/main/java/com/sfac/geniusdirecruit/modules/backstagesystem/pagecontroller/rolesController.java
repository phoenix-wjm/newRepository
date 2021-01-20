package com.sfac.geniusdirecruit.modules.backstagesystem.pagecontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/roles")
public class rolesController {
    /**
     * http://127.0.0.1:8080/roles/rolePage
     */
    @RequestMapping("/rolePage")
    public String rolePage(ModelMap modelMap) {
        modelMap.put("template", "backstagesystem/role/roles");
        return "common/managerIndex";
    }
}
