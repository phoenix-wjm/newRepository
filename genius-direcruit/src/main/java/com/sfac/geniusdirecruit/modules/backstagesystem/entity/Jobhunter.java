package com.sfac.geniusdirecruit.modules.backstagesystem.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/30 23:01
 * 概要：
 * 求职者
 */
@Entity
@Table(name = "jobhunter")
@Data
public class Jobhunter {
    //创建主键
    @Id//主键列
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobHunterId;

    private Integer userId;

    //求职者姓名
    private String jobHunterName;

    //性别
    private String sex;

    //生日
    private String birth;

    //头像
    private String photo;

    //学历
    private String educate;

    //邮箱
    private String email;

    //地址
    private String address;
}
