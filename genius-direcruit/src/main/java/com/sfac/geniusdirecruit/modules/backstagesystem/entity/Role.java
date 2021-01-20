package com.sfac.geniusdirecruit.modules.backstagesystem.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/30 23:24
 * 概要：
 * 角色
 */
@Entity
@Table(name = "role")
@Data
public class Role {
    //创建主键
    @Id//主键列
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    //角色名称
    private String roleName;

    //角色描述
    private String roleDescribe;

    @Transient
    private List<User> users;
}
