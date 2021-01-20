package com.sfac.geniusdirecruit.modules.backstagesystem.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Author: yzs
 * @Date: 2021/1/2 20:05
 * 概要：
 * XXXXX
 */
@Entity
@Table(name = "company_job")
@Data
public class CompanyJob {
    //创建主键
    @Id//主键列
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer companyJobId;

    private Integer companyId;

    private Integer jobId;
}

