package com.sfac.geniusdirecruit.modules.backstagesystem.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: yzs
 * @Date: 2020/12/31 0:09
 * 概要：
 * 简历
 */
@Entity
@Table(name = "resume")
@Data
public class Resume {
    //创建主键
    @Id//主键列
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer resumeId;
    private Integer jobHunterId;

    //简历名称
    private String resumeName;

    //姓名
    private String name;

    //寸照
    private String url;

    //学校名称
    private String schoolName;

    //当前求职状态（离职-随时到岗、在职-暂不考虑、在职-考虑机会、在职-月内到岗）
    private String state;

    //性别
    private String sex;

    //出生年月
    private String birthDate;

    //邮箱
    private String email;

    //电话
    private String tel;

    //个人优点
    private String personalAdv;

    //求职类型（全职、兼职）
    private String type;

    //工作城市
    private String workCity;

    //期望工作
    private String wantedJob;

    //期望行业
    private String wantedTrade;

    //薪资要求
    private String salary;

    //实习经历
    private String internship;

    //学历(初中及以下、中专/中技、高中、大专、本科、研究生、博士)
    private String education;

    //专业
    private String major;

    //时间段
    private String timeQuantum;
}
