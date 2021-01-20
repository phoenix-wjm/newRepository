package com.sfac.geniusdirecruit.modules.backstagesystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.dao.NewsDao;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.News;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.User;
import com.sfac.geniusdirecruit.modules.backstagesystem.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:28
 * 概要：
 * XXXXX
 */
@Service
public class NewsServiceImpl implements NewsService {
    @Autowired
    private NewsDao newsDao;
    @Override
    public ResultEntity<News> insertNews(News news) {
        News temp = newsDao.selectNewsByTitle(news.getTitle());
        if (temp == null) {
            newsDao.insertNews(news);
            return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                    "Insert success", news);
        }
            return new ResultEntity<>(ResultEntity.ResultStatus.FAILED.status,
                "Category name is repeat.");
    }

    @Override
    public List<News> getNews() {
        return Optional
                .ofNullable(newsDao.getNews())
                .orElse(Collections.emptyList());
    }

    @Override
    public PageInfo<News> getNewsBySearchBean(SearchBean searchBean) {
        searchBean.initSearchBean();
        PageHelper.startPage(searchBean.getCurrentPage(), searchBean.getPageSize());
        return new PageInfo<>(Optional
                .ofNullable(newsDao.getNewsBySearchBean(searchBean))
                .orElse(Collections.emptyList()));
    }

    @Override
    public News getNewsById(Integer newId) {
        return newsDao.getNewsById(newId);
    }

    @Override
    public ResultEntity<News> updateNews(News news) {
        News temp = newsDao.selectNewsByTitle(news.getTitle());
        if (temp == null||temp.getNewId()==news.getNewId()) {
            newsDao.updateNews(news);
            return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                    "Update success", news);
        }else
        return new ResultEntity<>(ResultEntity.ResultStatus.FAILED.status,
                "news title is repeat.");
    }

    @Override
    public ResultEntity<Object> deleteNewsById(Integer newId) {
            newsDao.deleteNewsById(newId);
            return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,
                    "Delete success");
        }
}