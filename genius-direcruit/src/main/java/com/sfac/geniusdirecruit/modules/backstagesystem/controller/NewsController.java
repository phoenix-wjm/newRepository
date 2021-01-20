package com.sfac.geniusdirecruit.modules.backstagesystem.controller;

import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.News;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.User;
import com.sfac.geniusdirecruit.modules.backstagesystem.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:12
 * 概要：
 * XXXXX
 */
@RestController
@RequestMapping("/api")
public class NewsController {
    @Autowired
    private NewsService newsService;

    //添加news http://127.0.0.1:8080/api/news
    //{"title":"习大大","content":"ygfeueuigvdu","createTime":"2020-01-05 14:33:34"}
    @PostMapping(value = "/news", consumes = "application/json")
    public ResultEntity<News> insertNews(@RequestBody News news) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime ldt = LocalDateTime.parse(format,df);
        news.setCreateTime(ldt);
        return newsService.insertNews(news);
    }

    //http://127.0.0.1:8080/api/newses  get请求
    @GetMapping("/newses")
    public List<News> getNews() {
        return newsService.getNews();
    }

    //http://127.0.0.1:8080/api/newses   post请求
    // {"currentPage":3, "pageSize":2, "order":"create_time", "direction":"desc", "keyWord":""}
    @PostMapping(value = "/newses", consumes = "application/json")
    public PageInfo<News> getNewsBySearchBean(
            @RequestBody SearchBean searchBean) {
        return newsService.getNewsBySearchBean(searchBean);
    }

    //预编辑  http://127.0.0.1:8080/api/news/1
    @GetMapping("/news/{newId}")
    public News getnewsById(@PathVariable("newId") Integer newId) {
        return newsService.getNewsById(newId);
    }

    //编辑  http://127.0.0.1:8080/api/news
    //{"newId":"3","title":"习大大","content":"ygfeueuigvdu","createTime":"2020-01-06 14:33:34"}
    @PutMapping(value = "/news", consumes = "application/json")
    public ResultEntity<News> updateNews(@RequestBody News news) {
        return newsService.updateNews(news);
    }

    //删除news http://127.0.0.1:8080/api/news/1
    @DeleteMapping("/news/{newId}")
    public ResultEntity<Object> deleteNewsById(@PathVariable("newId") Integer newId) {
        return newsService.deleteNewsById(newId);
    }
}

