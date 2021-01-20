package com.sfac.geniusdirecruit.modules.backstagesystem.dao;

import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Message;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.News;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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
public interface MessageDao {
    @Select("select * from message where title = #{title}")
    News selectMessageByTitle(String title);

    @Insert("insert into message (user_id,job_id,title,content,message_time) values (#{userId},#{jobId},#{title},#{content},#{messageTime})")
    void insertMessage(Message message);

    @Delete("delete from message where message_id = #{messageId}")
    void deleteMessageById(Integer messageId);
    //查询Message数据
    @Select("select * from message")
    List<Message> getMessage();
    //分页排序
    @Select("<script>" +
            "select * from message "
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
            + " order by message_time desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<Message> getMessageBySearchBean(SearchBean searchBean);

    @Select("select * from message where user_id=#{userId}")
    List<Message> selectMessagesByUserId(Integer userId);

    @Delete("delete from message where user_id = #{userId}")
    void deleteMessagesByUserId(Integer userId);
}
