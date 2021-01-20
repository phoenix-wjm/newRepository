package com.sfac.geniusdirecruit.modules.frontdesksystem.dao;

import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Job;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

//@Repository
//@Mapper
public interface JobsFrontDao extends PagingAndSortingRepository<Job, Integer>, JpaRepository<Job, Integer>, JpaSpecificationExecutor<Job> {
    //@Select("select * from job j left join job_category jc on j.job_category_id = jc.job_category_id")
    @Query(nativeQuery = true, value = "select * from job j left join job_category jc on j.job_category_id = jc.job_category_id")
    Page<Job> findAll(Pageable pageable);
}
