package com.sfac.geniusdirecruit.modules.backstagesystem.service;

import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Role;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.UserRole;


import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:29
 * 概要：
 * XXXXX
 */
public interface RoleService {
    List<Role> selectAllRoles();

    PageInfo<Role> getRolesBySearchBean(SearchBean searchBean);

    ResultEntity<Role> insertRole(Role role);

    Role getRoleByRoleId(int roleId);

    ResultEntity<Role> editRole(Role role);

    ResultEntity<Object> deleteRoleByRoleId(Integer roleId);

    UserRole selectUserRoleByUserId(Integer userId);

    Role selectRoleByRoleId(Integer roleId);
    Role selectRoleByUserId(Integer userId);

    List<Role> getRoleByUserId(Integer userId);
}
