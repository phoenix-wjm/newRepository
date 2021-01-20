package com.sfac.geniusdirecruit.modules.backstagesystem.service;
import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Job;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Resume;

import java.util.HashMap;
import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:25
 * 概要：
 * XXXXX
 */
public interface JobService {

    ResultEntity<Job> insertJob(Job job);

    Job getJobIdById(Integer jobId);

    List<Job> getJob();

    PageInfo<Job> getJobBySearchBean(SearchBean searchBean);

    ResultEntity<Job> updateJob(Job job);

    ResultEntity<Object> deleteJobById(Integer jobId);

}
