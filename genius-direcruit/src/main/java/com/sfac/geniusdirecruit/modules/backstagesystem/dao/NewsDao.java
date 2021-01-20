package com.sfac.geniusdirecruit.modules.backstagesystem.dao;

import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.News;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:18
 * 概要：
 * XXXXX
 */
@Repository
@Mapper
public interface NewsDao {

    //添加news
    @Insert("insert into news (title,content,create_time) values (#{title},#{content},#{createTime})")
    void insertNews(News news);

    //根据标题查询
    @Select("select * from news where title = #{title}")
    News selectNewsByTitle(String title);

    //查询news数据
    @Select("select * from news")
    List<News> getNews();

    //分页排序
    @Select("<script>" +
            "select * from news "
            + "<where> "
            + "<if test='keyWord != \"\" and keyWord != null'>"
            + " and (title like '%${keyWord}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='order != \"\" and order != null'>"
            + " order by ${order} ${direction}"
            + "</when>"
            + "<otherwise>"
            + " order by create_time desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<News> getNewsBySearchBean(SearchBean searchBean);
    //根据newId查询
    @Select("select * from news where new_id = #{newId}")
    News getNewsById(Integer newId);
    @Update("update news set title=#{title},content=#{content},create_time=#{createTime} where new_id = #{newId}")
    void updateNews(News news);
    //删除news
    @Delete("delete from news where new_id = #{newId}")
    void deleteNewsById(Integer newId);
}
