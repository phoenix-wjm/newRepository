package com.sfac.geniusdirecruit.modules.backstagesystem.pagecontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: yzs
 * @Date: 2021/1/11 14:31
 * 概要：
 * XXXXX
 */
@Controller
public class NewsesController {
    /**
     * http://127.0.0.1:8080/newses/newsPage
     */
    @GetMapping("/newses/newsPage")
    public String newsPage(ModelMap modelMap){
        modelMap.put("template", "backstagesystem/news/newses");
        return "common/managerIndex";
    }
}
