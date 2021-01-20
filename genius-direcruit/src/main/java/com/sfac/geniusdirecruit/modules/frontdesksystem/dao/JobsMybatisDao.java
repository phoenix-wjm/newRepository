package com.sfac.geniusdirecruit.modules.frontdesksystem.dao;

import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Job;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface JobsMybatisDao {
    //"SELECT\n" +
    //            "company.company_id,\n" +
    //            "company.company_name,\n" +
    //            "company.address,\n" +
    //            "company.description,\n" +
    //            "company.credit_code,\n" +
    //            "company.nature,\n" +
    //            "job.job_id,\n" +
    //            "job.job_category_id,\n" +
    //            "job.job_name,\n" +
    //            "job.description,\n" +
    //            "job.pay,\n" +
    //            "job.numbers,\n" +
    //            "job.degree,\n" +
    //            "job.expiry_date,\n" +
    //            "job.area,\n" +
    //            "job.address,\n" +
    //            "job.view_count,\n" +
    //            "job.release_time,\n" +
    //            "job_category.job_category_name\n" +
    //            "FROM\n" +
    //            "company_job\n" +
    //            "INNER JOIN job ON company_job.job_id = job.job_id AND company_job.job_id = job.job_id\n" +
    //            "INNER JOIN job_category ON job.job_category_id = 1 AND job.job_category_id = job_category.job_category_id\n" +
    //            "INNER JOIN company ON company.company_id = company_job.company_id"

    @Select("SELECT\n" +
            "job_category.job_category_name,\n" +
            "company.company_id,\n" +
            "company.user_id,\n" +
            "company.company_name,\n" +
            "company.address,\n" +
            "company.description,\n" +
            "company.credit_code,\n" +
            "company.nature,\n" +
            "job.job_id,\n" +
            "job.job_category_id,\n" +
            "job.job_name,\n" +
            "job.description,\n" +
            "job.pay,\n" +
            "job.numbers,\n" +
            "job.degree,\n" +
            "job.expiry_date,\n" +
            "job.area,\n" +
            "job.address,\n" +
            "job.view_count,\n" +
            "job.release_time\n" +
            "FROM\n" +
            "job\n" +
            "INNER JOIN job_category ON job.job_category_id = job_category.job_category_id\n" +
            "INNER JOIN company_job ON job.job_id = company_job.job_id\n" +
            "INNER JOIN company ON company_job.company_id = company.company_id")
    List<Job> findAll();

    @Select("SELECT\n" +
            "job_category.job_category_name,\n" +
            "company.company_id,\n" +
            "company.user_id,\n" +
            "company.company_name,\n" +
            "company.address,\n" +
            "company.description,\n" +
            "company.credit_code,\n" +
            "company.nature,\n" +
            "job.job_id,\n" +
            "job.job_category_id,\n" +
            "job.job_name,\n" +
            "job.description,\n" +
            "job.pay,\n" +
            "job.numbers,\n" +
            "job.degree,\n" +
            "job.expiry_date,\n" +
            "job.area,\n" +
            "job.address,\n" +
            "job.view_count,\n" +
            "job.release_time\n" +
            "FROM\n" +
            "job\n" +
            "INNER JOIN job_category ON job.job_category_id = job_category.job_category_id\n" +
            "INNER JOIN company_job ON job.job_id = company_job.job_id\n" +
            "INNER JOIN company ON company_job.company_id = company.company_id\n" +
            "where job.job_name LIKE #{search} OR job.degree LIKE #{search} or job.area like #{search} or job.pay like #{search}\n" +
            "or job.numbers like #{search} or company_name like #{search} or job_category_name like #{search}")
    List<Job> findBySearch(String search);
}
