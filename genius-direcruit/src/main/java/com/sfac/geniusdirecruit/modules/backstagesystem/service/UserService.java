package com.sfac.geniusdirecruit.modules.backstagesystem.service;

import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Company;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Jobhunter;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.User;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.vo.UserVo;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;


/**
 * @Author: yzs
 * @Date: 2020/12/31 16:30
 * 概要：
 * XXXXX
 */
public interface UserService {
    List<User> selectAllUser();

    ResultEntity<User> insertUser(UserVo userVo, HttpServletRequest request);

    User getUserById(int userId);

    ResultEntity<User> editUser(User user);

    PageInfo<User> getUsersBySearchBean(SearchBean searchBean);

    ResultEntity<Object> deleteUserByUserId(Integer userId);

    HashMap<Object, String> loginIn(User user, HttpServletRequest request);

    void logout();
	
    ResultEntity<User> login(User user);

    User selectUserByUserName(String userName);


    HashMap<String, Object> sendCode(String email, HttpServletRequest request);




    //发送短信
    HashMap<String, Object> sendSms(String phone, HttpServletRequest request);

    //手机短信进入
    HashMap<String, Object> smsEnter(UserVo userVo, HttpServletRequest request);

    HashMap<String, Object> sendMessage(String tel, HttpServletRequest request);

    HashMap<String, Object> emailLogin(String email, Integer code, HttpServletRequest request);

    HashMap<String, Object> messageLogin(String tel, Integer code, HttpServletRequest request);

    //求职者User表基本信息添加
    HashMap<Object, String> registerStaffOne(User user, String flag, HttpServletRequest request);

    //求职者Jobhunter表信息添加
    HashMap<Object, String> registerStaffTwo(Jobhunter jobhunter, HttpServletRequest request);

    ResultEntity<User> insertUserByAdmin(User user);
    //企业Company表信息添加
    HashMap<Object, String> registerCompany(Company company, HttpServletRequest request);

    HashMap<Object, String> ChangePassword(String newPassword, HttpServletRequest request);

}
