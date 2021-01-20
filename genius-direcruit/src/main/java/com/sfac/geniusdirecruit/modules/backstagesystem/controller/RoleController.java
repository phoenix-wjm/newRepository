package com.sfac.geniusdirecruit.modules.backstagesystem.controller;

import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Role;
import com.sfac.geniusdirecruit.modules.backstagesystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:13
 * 概要：
 * XXXXX
 */
@RestController
@RequestMapping("/api")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 查看角色信息
     * http://127.0.0.1:8080/api/roles-----------get
     */
    @GetMapping("/roles")
    public List<Role> selectAllRoles(){
        return roleService.selectAllRoles();
    }

    /**
     *查询所有角色信息并分页
     *  http://127.0.0.1:8080/api/roles--------post
     *  {"currentPage":1, "pageSize":2, "order":"role_id", "direction":"desc", "keyWord":""}
     */
    @PostMapping(value = "/roles",consumes = "application/json")
    public PageInfo<Role> getRolesBySearchBean(@RequestBody SearchBean searchBean){
        return roleService.getRolesBySearchBean(searchBean);
    }

    /**
     *添加角色
     * http://127.0.0.1:8080/api/role--------post
     * {"roleName":"common","roleDescribe":"普通用户"}
     */
    @PostMapping(value = "/role",consumes = "application/json")
    public ResultEntity<Role> insertRole(@RequestBody Role role){
        return roleService.insertRole(role);
    }

    /**
     * 通过roleId查询角色 ,预编辑
     * http://127.0.0.1:8080/api/role/1---------get
     */
    @GetMapping("/role/{roleId}")
    public Role getRoleByRoleId(@PathVariable int roleId) {
        return roleService.getRoleByRoleId(roleId);
    }

    /**
     * 编辑角色
     *http://127.0.0.1:8080/api/role---------put
     * {"roleId":6,"roleName":"common","roleDescribe":"khdkjsgdf"}
     */
    @PutMapping(value = "/role",consumes = "application/json")
    public ResultEntity<Role> editRole(@RequestBody Role role){
        return roleService.editRole(role);
    }

    /**
     * 删除角色
     * http://127.0.0.1:8080/api/role/5---------delete
     */
    @DeleteMapping("/role/{roleId}")
    public ResultEntity<Object> deleteRoleByRoleId(@PathVariable Integer roleId){
        return roleService.deleteRoleByRoleId(roleId);
    }

    /**
     * 通过userId查询角色
     * http://127.0.0.1:8080/api/role/userId/1---------get
     */
    @GetMapping("/role/userId/{userId}")
    public  List<Role> getRoleByUserId(@PathVariable int userId) {
        return roleService.getRoleByUserId(userId);
    }

}
