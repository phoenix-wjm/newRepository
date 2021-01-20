package com.sfac.geniusdirecruit.modules.backstagesystem.dao;

import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Job;
import org.apache.ibatis.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;


/**
 * @Author: yzs
 * @Date: 2020/12/31 16:17
 * 概要：
 * XXXXX
 */
@Repository
@Mapper
public interface JobDao {
    //添加job
    @Insert("Insert into job (job_category_id,job_name,description,pay,numbers,degree,expiry_date,area,address,view_count,release_time) " +
            "values (#{jobCategoryId},#{jobName},#{description},#{pay},#{numbers},#{degree},#{expiryDate},#{area},#{address},#{viewCount},#{releaseTime})")
    void insertJob(Job job);

    //根据jobName查询job
    @Select("select* from job where job_name=#{jobName}")
    Job selectJobByJobName(String jobName);

    //根据jobId查询job
    @Select("select* from job where job_id=#{jobId}")
    Job getJobIdById(Integer jobId);

    //查询job
    @Select("select * from job")
    List<Job> getJob();

    //分页排序查询job
    @Select("<script>" +
            "select * from job "
            + "<where> "
            + "<if test='keyWord != \"\" and keyWord != null'>"
            + " and (job_name like '%${keyWord}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='order != \"\" and order != null'>"
            + " order by ${order} ${direction}"
            + "</when>"
            + "<otherwise>"
            + " order by job_id desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<Job> getJobBySearchBean(SearchBean searchBean);
    //修改job
    @Update("update job set job_category_id=#{jobCategoryId},job_name=#{jobName},description=#{description},pay=#{pay},numbers=#{numbers},degree=#{degree},expiry_date=#{expiryDate},area=#{area},address=#{address},view_count=#{viewCount},release_time=#{releaseTime} where job_id = #{jobId}")
    void updateJob(Job job);

    //删除job
    @Delete("delete from job where job_id = #{jobId}")
    void deleteJobById(int jobId);

    //将url增加到简历表中
    @Insert("insert into resume (url) values (#{url})")
    int insertByUrl(String url);

    //通过jobId批量查询jobName
    @Select({"<script>" +
            "select job_name from job where job_id in " +
            "<foreach item = 'item' index = 'index' collection = 'jobIdList' open='(' separator=',' close=')'>" +
            "#{item}" +
            "</foreach>"+
            "</script>"})
    List<String> selectJobNameByIds(@Param("jobIdList") List<Integer> jobIdList);
}
