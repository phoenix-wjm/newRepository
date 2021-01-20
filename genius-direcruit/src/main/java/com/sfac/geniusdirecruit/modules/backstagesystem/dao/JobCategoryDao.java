package com.sfac.geniusdirecruit.modules.backstagesystem.dao;

import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.JobCategory;
import org.apache.ibatis.annotations.*;
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
public interface JobCategoryDao {
    @Select("select * from job_category")
    List<JobCategory> selectJobCategories();

    @Select("<script>" +
            "select * from job_category "
            + "<where> "
            + "<if test='keyWord != \"\" and keyWord != null'>"
            + " and (user_name like '%${keyWord}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='order != \"\" and order != null'>"
            + " order by ${order} ${direction}"
            + "</when>"
            + "<otherwise>"
            + " order by job_category_id desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<JobCategory> getJobCategoriesBySearchBean(SearchBean searchBean);

    @Insert("insert into job_category (job_category_name) values (#{jobCategoryName})")
    void insertJobCategory(JobCategory jobCategory);

    @Select("select * from job_category where job_category_id = #{jobCategoryId}")
    JobCategory getJobCategoryByJobCategoryId(int jobCategoryId);

    @Update("update job_category set job_category_name = #{jobCategoryName} where job_category_id = #{jobCategoryId}")
    void editJobCategory(JobCategory jobCategory);

    @Delete("delete from job_category where job_category_id = #{jobCategoryId}")
    void deleteJobCategoryByJobCategoryId(Integer jobCategoryId);
}
