package com.sfac.geniusdirecruit.modules.backstagesystem.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: yzs
 * @Date: 2020/12/30 23:59
 * 概要：
 * 友情链接
 */
@Entity
@Table(name = "blogroll")
@Data
public class Blogroll {
    //创建主键
    @Id//主键列
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer blogrollId;

    //链接名称
    private String blogrollName;

    //链接徽标
    private String blogrollLogo;

    //链接网址
    private String blogrollUrl;
}
