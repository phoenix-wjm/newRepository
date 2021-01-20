package com.sfac.geniusdirecruit.modules.backstagesystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.dao.JobCategoryDao;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.JobCategory;
import com.sfac.geniusdirecruit.modules.backstagesystem.service.JobCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:26
 * 概要：
 * XXXXX
 */
@Service
public class JobCategoryServiceImpl implements JobCategoryService {
    @Autowired
    private JobCategoryDao jobCategoryDao;
    @Override
    public List<JobCategory> selectJobCategories() {
        return jobCategoryDao.selectJobCategories();
    }

    @Override
    public PageInfo<JobCategory> getJobCategoriesBySearchBean(SearchBean searchBean) {
        searchBean.initSearchBean();
        PageHelper.startPage(searchBean.getCurrentPage(), searchBean.getPageSize());
        return new PageInfo<>(Optional
                .ofNullable(jobCategoryDao.getJobCategoriesBySearchBean(searchBean))
                .orElse(Collections.emptyList()));
    }

    @Override
    @Transactional
    public ResultEntity<JobCategory> insertJobCategory(JobCategory jobCategory) {
        jobCategoryDao.insertJobCategory(jobCategory);
        return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                "insert success",jobCategory);
    }

    @Override
    public JobCategory getJobCategoryByJobCategoryId(int jobCategoryId) {
        return jobCategoryDao.getJobCategoryByJobCategoryId(jobCategoryId);
    }

    @Override
    public ResultEntity<JobCategory> editJobCategory(JobCategory jobCategory) {
        jobCategoryDao.editJobCategory(jobCategory);
        return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                "update success",jobCategory);
    }

    @Override
    public ResultEntity<Object> deleteJobCategoryByJobCategoryId(Integer jobCategoryId) {
        jobCategoryDao.deleteJobCategoryByJobCategoryId(jobCategoryId);
        return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                "delete success");
    }
}
