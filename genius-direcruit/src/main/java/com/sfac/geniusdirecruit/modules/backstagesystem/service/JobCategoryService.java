package com.sfac.geniusdirecruit.modules.backstagesystem.service;

import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.JobCategory;

import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:25
 * 概要：
 * XXXXX
 */
public interface JobCategoryService {
    List<JobCategory> selectJobCategories();

    PageInfo<JobCategory> getJobCategoriesBySearchBean(SearchBean searchBean);

    ResultEntity<JobCategory> insertJobCategory(JobCategory jobCategory);

    JobCategory getJobCategoryByJobCategoryId(int jobCategoryId);

    ResultEntity<JobCategory> editJobCategory(JobCategory jobCategory);

    ResultEntity<Object> deleteJobCategoryByJobCategoryId(Integer jobCategoryId);
}
