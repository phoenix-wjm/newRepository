package com.sfac.geniusdirecruit.modules.test.controller;

import com.sfac.geniusdirecruit.modules.backstagesystem.entity.User;
import com.sfac.geniusdirecruit.modules.backstagesystem.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @Author: yzs
 * @Date: 2020/12/30 22:21
 * 概要：
 * XXXXX
 */

@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    UserService userService;

    /**
     * http://localhost:8080/test/desc----get
     */
    @GetMapping("/desc")
    @ResponseBody
    public String testDesc(){
        return "This is a test!";
    }

    /**
     * http://localhost:8080/test/register----get
     */

    /*test12*/
    @RequestMapping("/register")
    public String testRegister(){
        System.err.println("从注册页面跳过来了哟");
        return "frontdesk/register";
    }

    @RequestMapping("/login")
    public String testlogin(){
        return "frontdesk/login";
    }

    //账号密码登录
    @PostMapping("/loginIn")
    @ResponseBody
    public HashMap<Object,String> loginIn(@RequestBody User user, HttpServletRequest request){
        HashMap<Object, String> map = userService.loginIn(user,request);

        return map;
    }

    @RequestMapping("/managerIndex")
    public String managerIndex(){
        return "common/managerIndex";
    }

    //向邮箱发送验证码
    @GetMapping("/emailSend")
    @ResponseBody
    public HashMap<String,Object> emailSend(@RequestParam String email, HttpServletRequest request){

        return userService.sendCode(email,request);

    }

    //向手机发送验证码
    @GetMapping("/messageSend")
    @ResponseBody
    public HashMap<String,Object> messageSend(@RequestParam String tel,HttpServletRequest request){

        return userService.sendMessage(tel,request);
    }

    //用邮箱+验证码登录
    @PostMapping("/emailLogin")
    @ResponseBody
    public HashMap<String,Object> emailLogin(@RequestParam String email,Integer code,HttpServletRequest request){
        return userService.emailLogin(email,code,request);
    }

    //用手机+验证码登录
    @PostMapping("/messageLogin")
    @ResponseBody
    public HashMap<String,Object> messageLogin(@RequestParam String tel,Integer code,HttpServletRequest request){
        return userService.messageLogin(tel,code,request);
    }

    //退出登录
    @RequestMapping("/loginout")
    @ResponseBody
    public HashMap<String,Object> logOut(ModelMap modelMap) {
        HashMap<String,Object> map = new HashMap<>();
        try{
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        Session session = subject.getSession();
        session.removeAttribute("user");
        map.put("info","退出登录成功");
        }catch (Exception e){
            map.put("info","退出失败");
        }
        return map;
    }
}
