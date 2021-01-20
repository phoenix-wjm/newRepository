package com.sfac.geniusdirecruit.modules.backstagesystem.pagecontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: yzs
 * @Date: 2021/1/11 14:32
 * 概要：
 * XXXXX
 */
@Controller
public class BlogrollsController {

    /**
     * http://127.0.0.1:8080/blogrolls/blogrollPage
     */
    @GetMapping("/blogrolls/blogrollPage")
    public String blogrollPage(ModelMap modelMap){
        modelMap.put("template", "backstagesystem/blogroll/blogrolls");
        return "common/managerIndex";
    }
}
