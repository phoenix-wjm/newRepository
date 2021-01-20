package com.sfac.geniusdirecruit.modules.backstagesystem.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: yzs
 * @Date: 2020/12/30 23:26
 * 概要：
 * 用户角色中间表
 */
@Entity
@Table(name = "user_role")
@Data
public class UserRole {
    //创建主键
    @Id//主键列
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userRoleId;

    private Integer userId;

    private Integer roleId;

    public UserRole() {
    }


    public UserRole(Integer userId, Integer roleId) {
        this.userId = userId;
        this.roleId = roleId;

    }

}
