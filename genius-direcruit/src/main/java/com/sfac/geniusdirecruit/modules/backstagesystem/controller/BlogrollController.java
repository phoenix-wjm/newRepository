package com.sfac.geniusdirecruit.modules.backstagesystem.controller;

import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Blogroll;
import com.sfac.geniusdirecruit.modules.backstagesystem.service.BlogrollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:09
 * 概要：
 * XXXXX
 */
@RestController
@RequestMapping("/api")
public class BlogrollController {
    @Autowired
    private BlogrollService blogrollService;
    //添加blogroll http://127.0.0.1:8080/api/blogroll
    //{"blogrollName":"百度", "blogrollLogo":1, "blogrollUrl":"www.baidu"}
    @PostMapping(value = "/blogroll", consumes = "application/json")
    public ResultEntity<Blogroll> insertBlogroll(@RequestBody Blogroll blogroll) {

        return blogrollService.insertBlogroll(blogroll);
    }

    //http://127.0.0.1:8080/api/blogrolls  get请求
    @GetMapping("/blogrolls")
    public List<Blogroll> getBlogroll() {
        return blogrollService.getBlogroll();
    }

    //http://127.0.0.1:8080/api/blogrolls   post请求
    // {"currentPage":1, "pageSize":2, "order":"blogroll_id", "direction":"desc", "keyWord":""}
    @PostMapping(value = "/blogrolls", consumes = "application/json")
    public PageInfo<Blogroll> getBlogrollBySearchBean(
            @RequestBody SearchBean searchBean) {
        return blogrollService.getBlogrollBySearchBean(searchBean);
    }

    //预编辑  http://127.0.0.1:8080/api/blogroll/1
    @GetMapping("/blogroll/{blogrollId}")
    public Blogroll getnewsById(@PathVariable("blogrollId") Integer blogrollId) {
        return blogrollService.getBlogrollById(blogrollId);
    }

    //编辑  http://127.0.0.1:8080/api/blogroll
    //{"blogrollId":1,"blogrollName":"腾讯", "blogrollLogo":2, "blogrollUrl":"www.baidu"}
    @PutMapping(value = "/blogroll", consumes = "application/json")
    public ResultEntity<Blogroll> updateBlogroll(@RequestBody Blogroll blogroll) {
        return blogrollService.updateBlogroll(blogroll);
    }

    //删除 http://127.0.0.1:8080/api/blogroll/1
    @DeleteMapping("/blogroll/{blogrollId}")
    public ResultEntity<Object> deleteBlogrollById(@PathVariable("blogrollId") Integer blogrollId) {
        return blogrollService.deleteBlogrollById(blogrollId);
    }
}
