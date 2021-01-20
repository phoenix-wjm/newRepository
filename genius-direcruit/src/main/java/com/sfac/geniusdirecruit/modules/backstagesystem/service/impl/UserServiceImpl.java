package com.sfac.geniusdirecruit.modules.backstagesystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.common.utile.EmailSend;
import com.sfac.geniusdirecruit.common.utile.MD5Util;
import com.sfac.geniusdirecruit.common.utile.SmsSend;
import com.sfac.geniusdirecruit.modules.backstagesystem.dao.*;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.*;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.vo.UserVo;
import com.sfac.geniusdirecruit.modules.backstagesystem.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:30
 * 概要：
 * XXXXX
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private CompanyDao companyDao;
    @Autowired
    private JobhunterDao jobhunterDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private EmailSend emailSend;
    @Autowired
    private RedisTemplate<String ,Object> redisTemplate;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    CompanyJobDao companyJobDao;
    @Autowired
    ResumeDao resumeDao;
    @Autowired
    MessageDao messageDao;


    @Autowired
    private SmsSend smsSend;

    @Override
    public List<User> selectAllUser() {
        List<User> users = userDao.selectAllUser();
        List<User> userList=new ArrayList<>();
        for (User user : users) {
            List<Role> roles = userRoleDao.selectRolesByUserId(user.getUserId());
            user.setRoles(roles);
            userList.add(user);
        }
        return userList;
    }

    @Override
    public PageInfo<User> getUsersBySearchBean(SearchBean searchBean) {
        searchBean.initSearchBean();
        PageHelper.startPage(searchBean.getCurrentPage(), searchBean.getPageSize());
        PageInfo<User> pageInfo = new PageInfo<User>(Optional
                .ofNullable(userDao.getUsersBySearchBean(searchBean))
                .orElse(Collections.emptyList()));
        pageInfo.getList().stream().forEach(item -> {
            List<Role> roles = userRoleDao.selectRolesByUserId(item.getUserId());
            item.setRoles(roles);
        });
        return pageInfo;
    }



    @Override
    @Transactional
    public ResultEntity<User> insertUser(UserVo userVo,HttpServletRequest request) {
        User user = new User();
        user.setUserPwd(userVo.getUserPwd());
        user.setUserName(userVo.getUserName());
        user.setTel(userVo.getTel());
        user.setState(1);
        User userTemp = userDao.selectUserByUserName(user.getUserName());
        User userTemp1 = userDao.findUsersByTel(userVo.getTel());
        if (userTemp1 != null) {
            return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                    "tel is registered.", user);
        }
        String sessionCode = request.getSession().getAttribute("smsCode")+"";
        if (sessionCode.equals(userVo.getCode())) {
            if (userTemp != null) {
                return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                        "User name is repeat.", user);
            }
            user.setUserPwd(MD5Util.getMD5(user.getUserPwd()));
            user.setCreateTime(LocalDateTime.now());
            int i = userDao.insertUser(user);
            User user1=userDao.selectUserByUserName(user.getUserName());
            if(i>0){
                UserRole userRole = new UserRole();
                userRole.setUserId(user1.getUserId());
                userRole.setRoleId(3);
                userRoleDao.insertRegisterUser(userRole);
            }


            return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                    "register success", user);

        }
        return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                "code is error.", user);
    }

    @Override
    public User getUserById(int userId) {
        List<Role> roles=userRoleDao.selectRolesByUserId(userId);
        User user = userDao.getUserById(userId);
        user.setRoles(roles);
        return  user;
    }

    @Override
    public ResultEntity<User> editUser(User user) {
        System.err.println("------------"+user.getUserId());
        User userById = userDao.getUserById(user.getUserId());
        userRoleDao.deleteByUserId(userById.getUserId());
        List<Role> roleList=user.getRoles();
        for (Role role : roleList) {
            userRoleDao.insertUserRole(user.getUserId(),role.getRoleId());
        }
        userDao.editUser(user);
        return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                "update success",user);
    }

    @Override
    public ResultEntity<Object> deleteUserByUserId(Integer userId) {
        Jobhunter jobHunter=jobhunterDao.selectJobHunterByUserId(userId);
        if (jobHunter!=null){
            resumeDao.deleteResumesByJobhunterId(jobHunter.getJobHunterId());
            jobhunterDao.deleteJobHunterByUserId(userId);
        }else{
            Company company=companyDao.selectCompanyByUserId(userId);
            if (company!=null){
                companyJobDao.deleteCompanyJobsByCompanyId(company.getCompanyId());
                companyDao.deleteCompanyByUserId(userId);
            }
        }
        messageDao.deleteMessagesByUserId(userId);
        List<UserRole> userRoles=userRoleDao.selectByUserId(userId);
        if (userRoles.size()!=0){
            userRoleDao.deleteByUserId(userId);
        }
        userDao.deleteUserByUserId(userId);
        return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                "delete success");
    }

    //判断用户是否被锁定的方法
    private boolean judgeAccount(String userName){
        String key=userName+":lock";
        Long lockTime=redisTemplate.getExpire(key, TimeUnit.SECONDS);
        if (lockTime>0){
            return false;
        }else {
            return true;
        }
    }

    //设置失败次数
    private void setFailCounts(String userName){
        String key=userName+":failCount";
        redisTemplate.opsForValue().increment(key,new Double(1));
    }

    //获取失败次数
    private int getFailCounts(String userName){
        String key=userName+":failCount";
        Object num=redisTemplate.opsForValue().get(key);
        if (num==null){
            return 0;
        }else {
            return (int)num;
        }
    }

    //用户登录
    @Override
    public HashMap<Object, String> loginIn(User user,HttpServletRequest request) {
        HashMap<Object,String> map = new HashMap<Object, String>();
        try {
            if (judgeAccount(user.getUserName())){
                UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),MD5Util.getMD5(user.getUserPwd()));
                Subject subject = SecurityUtils.getSubject();
                subject.login(token);
                subject.checkRoles();
                request.getSession().setAttribute("user",subject.getPrincipal());
                map.put("info","登录成功");
                redisTemplate.delete(user.getUserName()+":lock");
                redisTemplate.delete(user.getUserName()+":failCount");
                redisTemplate.opsForValue().set("userName",user.getUserName(),10,TimeUnit.MINUTES);
            }else {
                map.put("info","账号被锁定，请在1分钟后再次尝试");
            }
        }catch (UnknownAccountException e){
            e.printStackTrace();
            map.put("info","用户名或密码输入错误");
        }
        catch (IncorrectCredentialsException e){
            setFailCounts(user.getUserName());
            int num = getFailCounts(user.getUserName());
            if (num>=3){
                String key = user.getUserName()+":lock";
                redisTemplate.opsForValue().set(key,"userName",1,TimeUnit.MINUTES);
                String key1 = user.getUserName()+":failCount";
                redisTemplate.opsForValue().set(key1,1,1,TimeUnit.MINUTES);
                map.put("info","账号被锁定,请在1分钟后再次尝试");
            }else {
                map.put("info","你输入密码错误"+num+"次，还剩余"+(3-num)+"次机会");
            }
        }
        return map;
    }


    @Override
    public ResultEntity<User> login(User user) {
        try {
            Subject subject = SecurityUtils.getSubject();

            UsernamePasswordToken usernamePasswordToken =
                    new UsernamePasswordToken(user.getUserName(), MD5Util.getMD5(user.getUserPwd()));
//			usernamePasswordToken.setRememberMe(user.getRememberMe());

            subject.login(usernamePasswordToken);
            subject.checkRoles();
            Session session = subject.getSession();
            User userTemp = (User) subject.getPrincipal();
            session.setAttribute("userId", userTemp.getUserId());
            session.setAttribute("userName", userTemp.getUserName());
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultEntity<User>(ResultEntity.ResultStatus.SUCCESS.status, "User name or password error.");
        }

        return new ResultEntity<User>(ResultEntity.ResultStatus.SUCCESS.status, "Login success.", user);
    }


    @Override
    public User selectUserByUserName(String userName) {
        return userDao.selectUserByUserName(userName);
    }



    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        Session session = subject.getSession();
        session.removeAttribute("userId");
    }


    //发送验证码到邮箱
    @Override
    public HashMap<String, Object> sendCode(String email, HttpServletRequest request) {
        HashMap<String,Object> map = new HashMap<String, Object>();
        try {
            User user = userDao.selectUserByEmail(email);
            System.err.println(user);
            if (user==null){
                map.put("info","邮箱未注册，请先前往注册页面注册");
                return map;
            }else {
                int code = (int)((Math.random()*9+1)*1000);
                emailSend.send(email,"牛人直聘","您的验证码是："+code+"，请不要告诉他人");
                request.getSession().setAttribute("code",code);
                request.getSession().setAttribute("email",email);
                map.put("info","邮件发送成功，请查收");
                return map;
            }
        }catch (Exception e){
            map.put("info","邮件发送失败");
            return map;
        }
    }




    //发送短信
    @Override
    public HashMap<String, Object> sendSms(String phone, HttpServletRequest request) {

        HashMap<String,Object> map = new HashMap<String,Object>();

//        Random rd = new Random();
//        int code = rd.nextInt(1000);
//        //发送短信
//        String info = smsSend.send(phone,code+"");
//        //判断是否成功
//        if(info.equals("OK")){
//            //存入session中
//            request.getSession().setAttribute("smsCode",code);
//            map.put("info","短信发送成功");
//        }else{
//            map.put("info","短信发送失败");
//        }

        request.getSession().setAttribute("smsCode",1111);

        return map;
    }

    //手机短信进入
    @Override
    public HashMap<String, Object> smsEnter(UserVo userVo, HttpServletRequest request) {


                HashMap<String,Object> map = new HashMap<String,Object>();

        String sessionCode = request.getSession().getAttribute("smsCode")+"";

        if(userVo.getCode().equals(sessionCode)){

            System.err.println("smsEnter>>>>>>>>>>>>>>>"+userVo.getTel());

            System.err.println("smsEnter>>>>>>>>>>>>>>>"+userVo.getCode());


            //根据电话去查询用户，如果已注册，则会去登录页面
            User userTemp = userDao.selectUserByTel(userVo.getTel());
            if (userTemp!=null){
                map.put("info","用户已注册，请登录");
            }else {

                map.put("info","登入成功");
            }

        }else{
            map.put("info","登入失败");
        }

        return map;

    }

    //邮箱+验证码登录
    @Override
    public HashMap<String, Object> emailLogin(String email, Integer code, HttpServletRequest request) {
        HashMap<String,Object> map = new HashMap<String, Object>();
        User user = userDao.selectUserByEmail(email);

        Integer code0 = (Integer) request.getSession().getAttribute("code");
        String email0 = (String) request.getSession().getAttribute("email");
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getUserPwd());
        if (code.equals(code0)&&email.equals(email0)){
            map.put("info","登录成功");
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            subject.checkRoles();
            request.getSession().setAttribute("user",subject.getPrincipal());
            return map;
        }else {
            map.put("info","登录失败，请稍后重试");
            return map;
        }
    }

    //发送验证码到手机
    @Override
    public HashMap<String, Object> sendMessage(String tel, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<String, Object>();

            int code = (int)((Math.random()*9+1)*1000);
            String info = smsSend.send(tel,code+"");
            System.err.println(info);
            if (info.equals("OK")){
                //存入session中
                request.getSession().setAttribute("smsCode",code);
                request.getSession().setAttribute("tel",tel);
                map.put("info","短信发送成功");
                return map;
            }else{
                map.put("info","短信发送失败");
                return map;
            }
    }

    //手机+验证码登录
    @Override
    public HashMap<String, Object> messageLogin(String tel, Integer code, HttpServletRequest request) {
        HashMap<String,Object> map = new HashMap<String, Object>();
        User user = userDao.selectUserByTel(tel);
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUserName(),user.getUserPwd());
        Integer code0 = (Integer) request.getSession().getAttribute("smsCode");
        String tel0 = (String) request.getSession().getAttribute("tel");
        if (code.equals(code0)&&tel.equals(tel0)){
            map.put("info","登录成功");
            Subject subject = SecurityUtils.getSubject();
            subject.login(token);
            subject.checkRoles();
            request.getSession().setAttribute("user",subject.getPrincipal());
            return map;
        }else {
            map.put("info","登录失败，请稍后重试");
            return map;
        }
    }

    //求职者User表基本信息添加
    @Override
    @Transactional
    public HashMap<Object, String> registerStaffOne(User user, String flag, HttpServletRequest request) {


        HashMap<Object, String> map = new HashMap<Object, String>();


        User userTemp = userDao.findUsersByUsername(user.getUserName());

        if (userTemp !=null) {
            map.put("info","该用户名已被注册，请重新输入");
        }else {
            //设置时间
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
            String format = simpleDateFormat.format(new Date());
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime createTime = LocalDateTime.parse(format, df);
            user.setCreateTime(createTime);

            //设置状态
            user.setState(1);
            //加密
            user.setUserPwd(MD5Util.getMD5(user.getUserPwd()));
            userDao.insertRegisterUser(user);

            //操作中间表
            user.getRoles().stream().forEach(item -> {

                UserRole userRole = new UserRole(user.getUserId(), item.getRoleId());
                userRoleDao.insertRegisterUser(userRole);

            });


        /*if (user.getRoles() != null) {
            for(Role role : user.getRoles()) {
                UserRole userRole = new UserRole(user.getUserId(), role.getRoleId());
                userRoleDao.insertRegisterUser(userRole);
            }
        }*/

            request.getSession().setAttribute("userId",user.getUserId());

            System.err.println("registerStaffOne>>>>>>>>>>>>>>>>>>>>>>>>>>"+user.getUserId());

            System.err.println("registerStaffOne>>>>>>>>>>>>>>>>>>>>>>>>>>"+flag);

            if (flag.equals("1")){

                map.put("info","将填求职者信息表");

            }else{
                map.put("info","将填企业信息表");
            }


        }

        return map;

    }


    //求职者Jobhunter表信息添加（待优化）
    @Override
    @Transactional
    public HashMap<Object, String> registerStaffTwo(Jobhunter jobhunter, HttpServletRequest request) {

        System.err.println("registerStaffTwo>>>>>>>>>>>impl>>>>>>>>>>>>>>>"+jobhunter);

        HashMap<Object, String> map = new HashMap<Object, String>();

        User userTemp = userDao.selectUserByEmail(jobhunter.getEmail());
        System.err.println("registerStaffTwo>>>>>>>>>>>impl>>>>>>>>>>>>>>>"+userTemp);
        if (userTemp!=null){
            map.put("info","注册邮箱已被使用，请重新输入");
        }else {

            Integer userId = (Integer) request.getSession().getAttribute("userId");

            System.err.println("registerStaffTwo>>>>>>>>>>>impl>>>>>>>>>>>>>>>"+userId);

            jobhunter.setUserId(userId);

            jobhunterDao.insertJobHunter(jobhunter);

            map.put("info","注册成功");
        }


        return map;
    }



    @Override
    public ResultEntity<User> insertUserByAdmin(User user) {
        User user1=new User();
        user1.setUserName(user.getUserName());
        user1.setUserPwd(MD5Util.getMD5(user.getUserPwd()));
        user1.setTel(user.getTel());
        user1.setState(user.getState());
        user1.setCreateTime(user.getCreateTime());
        userDao.insertUser(user1);
        User user2 = userDao.findUsersByTel(user1.getTel());
        List<Role> roleList=user.getRoles();
        for (Role role : roleList) {
            userRoleDao.insertUserRole(user2.getUserId(),role.getRoleId());
        }
        return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                "insert success",user1);
    }

    //企业Company表信息添加
    @Override
    @Transactional
    public HashMap<Object, String> registerCompany(Company company, HttpServletRequest request) {
        HashMap<Object, String> map = new HashMap<Object, String>();

       Company companyTemp =  companyDao.selectCompanyByCompanyName(company.getCompanyName());

       if (companyTemp!=null){
           map.put("info","该公司名称已被注册，请核实后添加");
       }else {

           Integer userId = (Integer) request.getSession().getAttribute("userId");
           System.err.println("registerCompany>>>>>>>>>>>impl>>>>>>>>>>>>>>>"+userId);
           company.setUserId(userId);

           companyDao.insertCompanyAll(company);
           map.put("info","企业注册成功");

       }


        return map;

    }


    @Override
    public HashMap<Object, String> ChangePassword(String newPassword, HttpServletRequest request) {

        HashMap<Object, String> map = new HashMap<Object, String>();

        System.err.println("..进入了....ChangePassword.....impl........");


        User user1 = (User) request.getSession().getAttribute("user");
        Integer userId = user1.getUserId();
        System.err.println("..进入了....ChangePassword.....impl........"+userId);

        if (userId!=null){
            User user = new User();
            user.setUserId(userId);
            user.setUserPwd(MD5Util.getMD5(newPassword));

            System.err.println("...ChangePassword..MD5Util.getMD5......"+MD5Util.getMD5(newPassword));
            userDao.modifyPassword(user);
            map.put("info","修改密码成功");

        }else {
            map.put("info","修改密码失败");
        }


        return map;
    }


}
