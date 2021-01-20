package com.sfac.geniusdirecruit.modules.backstagesystem.pagecontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/companines")
public class companiesController {
    /**
     * http://127.0.0.1:8080/companines/companyPage
     */
    @RequestMapping("/companyPage")
    public String companyPage(ModelMap modelMap) {
        modelMap.put("template", "backstagesystem/company/companies");
        return "common/managerIndex";
    }
    /**
     * http://127.0.0.1:8080/companines/companyInfo
     */
    @RequestMapping("/companyInfo")
    public String companyInfo(ModelMap modelMap) {
        modelMap.put("template", "backstagesystem/company/companyInfo");
        return "common/managerIndex";
    }
}
