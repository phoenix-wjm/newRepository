package com.sfac.geniusdirecruit.modules.backstagesystem.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/30 23:17
 * 概要：
 * 企业
 */
@Entity
@Table(name = "company")
@Data
public class Company {
    //创建主键
    @Id//主键列
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyId;
    private Integer userId;

    //企业名称
    private String companyName;

    //公司地址
    private String address;

    //公司描述
    @Column
    private String description;

    //信用代码
    @Column
    private String creditCode;

    //公司性质
    @Column
    private String nature;

    @Transient
    private List<Job> jobs;

}
