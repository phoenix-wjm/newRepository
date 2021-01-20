package com.sfac.geniusdirecruit.modules.backstagesystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.dao.BlogrollDao;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Blogroll;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.News;
import com.sfac.geniusdirecruit.modules.backstagesystem.service.BlogrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:22
 * 概要：
 * XXXXX
 */
@Service
public class BlogrollServiceImpl implements BlogrollService {
    @Autowired
    private BlogrollDao blogrollDao;

    @Override
    public ResultEntity<Blogroll> insertBlogroll(Blogroll blogroll) {
        Blogroll temp = blogrollDao.selectblogrollNameByBlogrollName(blogroll.getBlogrollName());
        if (temp == null) {
            blogrollDao.insertBlogroll(blogroll);
            return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                    "Insert success", blogroll);
        }
            return new ResultEntity<>(ResultEntity.ResultStatus.FAILED.status,
                "blogroll name is repeat.");

    }

    @Override
    public List<Blogroll> getBlogroll() {
        return blogrollDao.getBlogroll();
    }

    @Override
    public PageInfo<Blogroll> getBlogrollBySearchBean(SearchBean searchBean) {
        searchBean.initSearchBean();
        PageHelper.startPage(searchBean.getCurrentPage(), searchBean.getPageSize());
        return new PageInfo<>(Optional
                .ofNullable(blogrollDao.getBlogrollBySearchBean(searchBean))
                .orElse(Collections.emptyList()));
    }

    @Override
    public Blogroll getBlogrollById(Integer blogrollId) {
        return blogrollDao.getBlogrollById(blogrollId);
    }

    @Override
    public ResultEntity<Blogroll> updateBlogroll(Blogroll blogroll) {
        blogrollDao.updateBlogroll(blogroll);
        return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                "update success");

    }

    @Override
    public ResultEntity<Object> deleteBlogrollById(Integer blogrollId) {
        blogrollDao.deleteBlogrollById(blogrollId);
        return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                "Delete success");

    }
}
