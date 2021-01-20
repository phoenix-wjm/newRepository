package com.sfac.geniusdirecruit.modules.backstagesystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/30 22:50
 * 概要：
 * 用户
 */
@Entity
@Table(name = "user")
@Data
public class User{
    //创建主键
    @Id//主键列
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    //用户名
    private String userName;

    //密码
    private String userPwd;

    //创建时间
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    //电话
    @Column(length = 11)
    private String tel;

    //状态
    private Integer state;

    @Transient
    private List<Role> roles;

}
