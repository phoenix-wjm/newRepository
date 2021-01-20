package com.sfac.geniusdirecruit.modules.backstagesystem.dao;

import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:19
 * 概要：
 * XXXXX
 */
@Repository
@Mapper
public interface UserDao {

    //用户名密码登录
    @Select("select * from user where user_name = #{userName} and user_pwd =#{userPwd}")
    User selectUserByUserNameAndPwd(String userName, String userPwd);

    @Select("select * from user")
    List<User> selectAllUser();

    @Select("<script>" +
            "select * from user "
            + "<where> "
            + "<if test='keyWord != \"\" and keyWord != null'>"
            + " and (user_name like '%${keyWord}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='order != \"\" and order != null'>"
            + " order by ${order} ${direction}"
            + "</when>"
            + "<otherwise>"
            + " order by create_time desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<User> getUsersBySearchBean(SearchBean searchBean);

    @Insert("insert into user (user_name,user_pwd,tel,state,create_time) values (#{userName},#{userPwd},#{tel},#{state},#{createTime})")
    int insertUser(User user);

    @Select("select * from user where user_id = #{userId}")
    User getUserById(int userId);

    @Update("update user set user_name = #{userName},tel = #{tel},state = #{state}, create_time = #{createTime} where user_id = #{userId}")
    void editUser(User user);

    @Delete("delete from user where user_id = #{userId}")
    void deleteUserByUserId(Integer userId);

    @Select("select * from user where user_name = #{userName} or tel = #{userName}")
    User selectUserByUserName(String userName);


    //通过用户名查找用户
    @Select("select * from user where user_name = #{userName}")
    User findUsersByUsername(String userName);

    //通过电话查找用户
    @Select("select * from user where tel = #{tel}")
    User findUsersByTel(String tel);


    //新增注册后的用户
    @Insert("insert into user (user_name,user_pwd,create_time,tel,state) values (#{userName},#{userPwd},#{createTime},#{tel},#{state})")
    @Options(useGeneratedKeys = true, keyProperty = "userId",keyColumn = "user_id")
    void insertRegisterUser(User user);

    //通过email查询User
    @Select("SELECT\n" +
            "`user`.user_id,\n" +
            "`user`.user_name,\n" +
            "`user`.user_pwd,\n" +
            "`user`.create_time,\n" +
            "`user`.tel,\n" +
            "`user`.state\n" +
            "FROM\n" +
            "jobhunter\n" +
            "INNER JOIN `user` ON jobhunter.user_id = `user`.user_id\n" +
            "where email = #{email}")
    User selectUserByEmail(String email);

    //通过电话查找用户
    @Select("select * from user where tel = #{tel}")
    User selectUserByTel(String tel);


    //通过userId批量查询userName
    @Select({"<script>" +
            "select user_name from user where user_id in " +
            "<foreach item = 'item' index = 'index' collection = 'userIdList' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>"+
            "</script>"})
    List<String> selectUserNameByIds(@Param("userIdList") List<Integer> userIdList);

    @Update("update user set user_pwd = #{userPwd} where user_id = #{userId}")
    void modifyPassword(User user);
}
