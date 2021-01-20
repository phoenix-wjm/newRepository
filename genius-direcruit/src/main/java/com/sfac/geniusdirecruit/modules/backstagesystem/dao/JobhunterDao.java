package com.sfac.geniusdirecruit.modules.backstagesystem.dao;

import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Jobhunter;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:17
 * 概要：
 * XXXXX
 */
@Repository
@Mapper
public interface JobhunterDao {

    @Select("select * from jobhunter")
    List<Jobhunter> getJobhunter();
    //分页排序
    @Select("<script>" +
            "select * from jobhunter "
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
            + " order by job_hunter_id desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<Jobhunter> getJobhunterBySearchBean();

    @Select("select * from jobhunter where user_id = #{userId}")
    Jobhunter selectJobHunterByUserId(Integer userId);

    @Delete("delete from jobhunter where user_id = #{userId}")
    void deleteJobHunterByUserId(Integer userId);

    @Insert("insert into jobhunter (user_id,job_hunter_name,sex,birth,photo,educate,email,address) values (#{userId},#{jobHunterName},#{sex},#{birth},#{photo},#{educate},#{email},#{address})")
    void insertJobHunter(Jobhunter jobhunter);

}
