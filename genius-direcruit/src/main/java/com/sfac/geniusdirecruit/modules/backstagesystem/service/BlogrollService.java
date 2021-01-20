package com.sfac.geniusdirecruit.modules.backstagesystem.service;

import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Blogroll;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.News;

import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:22
 * 概要：
 * XXXXX
 */
public interface BlogrollService {
    ResultEntity<Blogroll> insertBlogroll(Blogroll blogroll);

    List<Blogroll> getBlogroll();
    PageInfo<Blogroll> getBlogrollBySearchBean(SearchBean searchBean);

    Blogroll getBlogrollById(Integer newId);

    ResultEntity<Blogroll> updateBlogroll(Blogroll blogroll);

    ResultEntity<Object> deleteBlogrollById(Integer blogrollId);
}
