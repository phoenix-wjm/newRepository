package com.sfac.geniusdirecruit.modules.backstagesystem.service;

import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.News;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.User;

import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:28
 * 概要：
 * XXXXX
 */
public interface NewsService {
    ResultEntity<News> insertNews(News news);

    List<News> getNews();
    PageInfo<News> getNewsBySearchBean(SearchBean searchBean);

    News getNewsById(Integer newId);

    ResultEntity<News> updateNews(News news);

    ResultEntity<Object> deleteNewsById(Integer newId);
}
