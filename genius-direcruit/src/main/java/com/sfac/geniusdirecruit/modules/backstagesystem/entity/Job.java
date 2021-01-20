package com.sfac.geniusdirecruit.modules.backstagesystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/30 23:40
 * 概要：
 * 职位
 */
@Entity
@Table(name = "job")
@Data
public class Job extends MyPage {
    //创建主键
    @Id//主键列
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer jobId;

    private Integer jobCategoryId;

    //职位名称
    private String jobName;

    //职位描述
    private String description;

    //工作薪酬
    private String pay;

    //招聘人数
    private String numbers;

    //学历要求
    private String degree;

    //有效期限
    private String expiryDate;

    //工作区域
    private String area;

    //工作地址
    private String address;

    //游览次数
    private String viewCount;

    //发布时间
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime releaseTime;

    @Transient
    private List<Company> companies;

}
