package com.sfac.geniusdirecruit.modules.backstagesystem.dao;

import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Role;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
public interface UserRoleDao {
    @Select("select * from user_role where user_id=#{userId}")
    List<UserRole> selectByUserId(Integer userId);

    @Delete("delete from user_role where user_id = #{userId}")
    void deleteByUserId(Integer userId);


    //增加中间表
    @Insert("insert into user_role(user_id,role_id) values (#{userId},#{roleId}) ")
    void insertRegisterUser(UserRole userRole);

    @Delete("delete from user_role where role_id = #{roleId}")
    void deleteByRoleId(Integer roleId);

    @Select("SELECT r.role_id,r.role_name,r.role_describe FROM user_role ur INNER JOIN role r ON ur.role_id = r.role_id where user_id=#{userId}")
    List<Role> selectRolesByUserId(int userId);

    @Select("select * from user_role where user_id=#{userId}")
    UserRole selectUserRoleByUserId(Integer userId);

    @Insert("insert into user_role(user_id,role_id) values (#{userId},#{roleId}) ")
    void insertUserRole(Integer userId, Integer roleId);
}
