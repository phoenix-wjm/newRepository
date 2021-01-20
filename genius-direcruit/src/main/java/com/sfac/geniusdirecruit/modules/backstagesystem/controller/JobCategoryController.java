package com.sfac.geniusdirecruit.modules.backstagesystem.controller;

import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.JobCategory;
import com.sfac.geniusdirecruit.modules.backstagesystem.service.JobCategoryService;
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
public class JobCategoryController {
    @Autowired
    private JobCategoryService jobCategoryService;

    /**
     * 查看所有职位类别
     * http://127.0.0.1:8080/api/jobcategories------------get
     */
    @GetMapping("/jobcategories")
    public List<JobCategory> selectJobCategories(){
        return jobCategoryService.selectJobCategories();
    }

    /**
     * 查看职位类别并分页
     *  http://127.0.0.1:8080/api/jobcategories------------post
     *  {"currentPage":1, "pageSize":2, "order":"job_category_id", "direction":"desc", "keyWord":""}
     */
    @PostMapping(value = "/jobcategories",consumes = "application/json")
    public PageInfo<JobCategory> getJobCategoriesBySearchBean(@RequestBody SearchBean searchBean){
        return jobCategoryService.getJobCategoriesBySearchBean(searchBean);
    }

    /**
     * 加添加职位信息
     * http://127.0.0.1:8080/api/jobcategory------------post
     * {"jobCategoryName":"软件工程"}
     */
    @PostMapping(value = "/jobcategory",consumes = "application/json")
    public ResultEntity<JobCategory> insertJobCategory(@RequestBody JobCategory jobCategory){
        return jobCategoryService.insertJobCategory(jobCategory);
    }

    /**
     * 通过jobCategoryId查询用户,预编辑,修改职位信息(预修改信息的显示)
     * http://127.0.0.1:8080/api/jobcategory/1------------get
     */
    @GetMapping("/jobcategory/{jobCategoryId}")
    public JobCategory getJobCategoryByJobCategoryId(@PathVariable int jobCategoryId){
        return jobCategoryService.getJobCategoryByJobCategoryId(jobCategoryId);
    }

    /**
     * 修改职位信息
     * http://127.0.0.1:8080/api/jobcategory------------put
     * {"jobCategoryId":1,"jobCategoryName":"产品销售"}
     */
    @PutMapping(value = "/jobcategory",consumes = "application/json")
    public ResultEntity<JobCategory> editJobCategory(@RequestBody JobCategory jobCategory){
        return jobCategoryService.editJobCategory(jobCategory);
    }

    /**
     * 删除职位信息
     * http://127.0.0.1:8080/api/jobcategory/4------------delete
     */
    @DeleteMapping("/jobcategory/{jobCategoryId}")
    public ResultEntity<Object> deleteJobCategoryByJobCategoryId(@PathVariable Integer jobCategoryId){
        return jobCategoryService.deleteJobCategoryByJobCategoryId(jobCategoryId);
    }
}
