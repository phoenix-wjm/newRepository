package com.sfac.geniusdirecruit.modules.backstagesystem.pagecontroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: yzs
 * @Date: 2021/1/13 16:13
 * 概要：
 * XXXXX
 */
@Controller
public class MessagesController {
    /**
     * http://127.0.0.1:8080/messages/messagePage
     */
    @GetMapping("/messages/messagePage")
    public String blogrollPage(ModelMap modelMap){
        modelMap.put("template", "backstagesystem/message/messages");
        return "common/managerIndex";
    }
}
