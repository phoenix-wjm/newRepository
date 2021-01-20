package com.sfac.geniusdirecruit.modules.backstagesystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.dao.RoleDao;
import com.sfac.geniusdirecruit.modules.backstagesystem.dao.UserRoleDao;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Role;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.UserRole;
import com.sfac.geniusdirecruit.modules.backstagesystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:29
 * 概要：
 * XXXXX
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Override
    public List<Role> selectAllRoles() {
        return roleDao.selectAllRoles();
    }

    @Override
    public PageInfo<Role> getRolesBySearchBean(SearchBean searchBean) {
        searchBean.initSearchBean();
        PageHelper.startPage(searchBean.getCurrentPage(), searchBean.getPageSize());
        return new PageInfo<>(Optional
                .ofNullable(roleDao.getRolesBySearchBean(searchBean))
                .orElse(Collections.emptyList()));
    }

    @Override
    public ResultEntity<Role> insertRole(Role role) {
        roleDao.insertRole(role);
        return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                "insert success",role);
    }

    @Override
    public Role getRoleByRoleId(int roleId) {
        return roleDao.getRoleByRoleId(roleId);
    }

    @Transactional
    @Override
    public ResultEntity<Role> editRole(Role role) {
        roleDao.editRole(role);
        return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                "update success",role);
    }

    @Override
    public ResultEntity<Object> deleteRoleByRoleId(Integer roleId) {
        userRoleDao.deleteByRoleId(roleId);
        roleDao.deleteRoleByRoleId(roleId);
        return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                "Delete success");
    }

    @Override
    public UserRole selectUserRoleByUserId(Integer userId) {

        return userRoleDao.selectUserRoleByUserId(userId);
    }

    @Override
    public Role selectRoleByRoleId(Integer roleId) {

        return roleDao.getRoleByRoleId(roleId);
    }
    @Override
    public Role selectRoleByUserId(Integer userId) {
        return selectRoleByUserId(userId);
    }

    @Override
    public List<Role> getRoleByUserId(Integer userId) {
        return roleDao.getRoleByUserId(userId);
    }

}
