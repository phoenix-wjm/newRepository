package com.sfac.geniusdirecruit.modules.backstagesystem.dao;

import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Role;
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
public interface RoleDao {

    @Select("select * from role")
    List<Role> selectAllRoles();

    @Select("<script>" +
            "select * from role "
            + "<where> "
            + "<if test='keyWord != \"\" and keyWord != null'>"
            + " and (role_name like '%${keyWord}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='order != \"\" and order != null'>"
            + " order by ${order} ${direction}"
            + "</when>"
            + "<otherwise>"
            + " order by role_id desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<Role> getRolesBySearchBean(SearchBean searchBean);

    @Insert("insert into role (role_name,role_describe) values (#{roleName},#{roleDescribe})")
    void insertRole(Role role);

    @Select("select * from role where role_id = #{roleId}")
    Role getRoleByRoleId(int roleId);

    @Update("update role set role_name = #{roleName},role_describe = #{roleDescribe} where role_id = #{roleId}")
    void editRole(Role role);

    @Delete("delete from role where role_id = #{roleId}")
    void deleteRoleByRoleId(Integer roleId);

    @Select("select * from role r,user_role ur where r.role_id=ur.role_id and user_id=#{userId}")
    Role selectRoleByUserId(Integer userId);

    @Select("select * from role r,user_role ur where r.role_id=ur.role_id and user_id=#{userId}")
    List<Role> getRoleByUserId(Integer userId);

}
