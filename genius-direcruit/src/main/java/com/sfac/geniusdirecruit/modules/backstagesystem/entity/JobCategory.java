package com.sfac.geniusdirecruit.modules.backstagesystem.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/30 23:38
 * 概要：
 * 职位类别
 */
@Entity
@Table(name = "job_category")
@Data
public class JobCategory {
    //创建主键
    @Id//主键列
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobCategoryId;

    //职位类别名称
    private String jobCategoryName;
}
