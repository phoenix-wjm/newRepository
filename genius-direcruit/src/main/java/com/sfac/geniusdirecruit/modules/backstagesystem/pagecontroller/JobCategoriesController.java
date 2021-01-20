package com.sfac.geniusdirecruit.modules.backstagesystem.pagecontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/jobcategories")
public class JobCategoriesController {
    @RequestMapping("/jobcategoriesPage")
    public String jobCategories(ModelMap modelMap) {
        modelMap.put("template", "backstagesystem/jobcategory/jobCategories");
        return "common/managerIndex";

    }
}
