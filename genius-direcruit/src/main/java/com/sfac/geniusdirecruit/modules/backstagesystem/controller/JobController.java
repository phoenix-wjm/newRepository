package com.sfac.geniusdirecruit.modules.backstagesystem.controller;

import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Job;
import com.sfac.geniusdirecruit.modules.backstagesystem.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:10
 * 概要：
 * XXXXX
 */
@RestController
@RequestMapping("/api")
public class JobController {
    @Autowired
    private JobService jobService;

    //添加 http://127.0.0.1:8080/api/job
   //{"jobCategoryId":"1","jobName":"习大大","description":"ygfeueuigvdu","pay":"5000","numbers":"21","degree":"大专","expiryDate":"1","area":"习大大","address":"ygfeueuigvdu","viewCount":"5000","releaseTime":"2021-01-05 14:33:34"}
    @PostMapping(value = "/job", consumes = "application/json")
    public ResultEntity<Job> insertJob(@RequestBody Job job) {
        return jobService.insertJob(job);
    }

    // http://127.0.0.1:8080/api/job/2
    @GetMapping("/job/{jobId}")
    public Job getJobIdById(@PathVariable("jobId") Integer jobId) {
        return jobService.getJobIdById(jobId);
    }

    //http://127.0.0.1:8080/api/jobs  get请求
    @GetMapping("/jobs")
    public List<Job> getJob() {
        return jobService.getJob();
    }

    //http://127.0.0.1:8080/api/jobs   post请求
    // {"currentPage":3, "pageSize":2, "order":"create_time", "direction":"desc", "keyWord":""}
    @PostMapping(value = "/jobs", consumes = "application/json")
    public PageInfo<Job> getJobBySearchBean(
            @RequestBody SearchBean searchBean) {
        System.err.println(123456789);
        PageInfo<Job> jobBySearchBean = jobService.getJobBySearchBean(searchBean);
        System.err.println(jobBySearchBean);
        return jobBySearchBean;
    }
    //编辑  http://127.0.0.1:8080/api/job
    @PutMapping(value = "/job", consumes = "application/json")
    public ResultEntity<Job> updateJob(@RequestBody Job job) {
        return jobService.updateJob(job);
    }

    //删除 http://127.0.0.1:8080/api/job/1
    @DeleteMapping("/job/{jobId}")
    public ResultEntity<Object> deleteJobById(@PathVariable("jobId") Integer jobId) {
        return jobService.deleteJobById(jobId);
    }
}
