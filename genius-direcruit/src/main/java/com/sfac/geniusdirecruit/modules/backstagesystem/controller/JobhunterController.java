package com.sfac.geniusdirecruit.modules.backstagesystem.controller;

import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Jobhunter;
import com.sfac.geniusdirecruit.modules.backstagesystem.service.JobhunterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:11
 * 概要：
 * XXXXX
 */
@RestController
@RequestMapping("/api")
public class JobhunterController {
    @Autowired
    private JobhunterService jobhunterService;
    //查询jobHunters
    //http://127.0.0.1:8080/api/jobhunters  get请求
    @GetMapping("/jobhunters")
    public List<Jobhunter> getLeaveWord() {
        return jobhunterService.getJobhunter();
    }
    //http://127.0.0.1:8080/api/jobhunters  post请求
    @PostMapping(value = "/jobhunters", consumes = "application/json")
    public PageInfo<Jobhunter> getLeaveWordBySearchBean(
            @RequestBody SearchBean searchBean) {
        return jobhunterService.getJobhunterBySearchBean(searchBean);
    }
}
