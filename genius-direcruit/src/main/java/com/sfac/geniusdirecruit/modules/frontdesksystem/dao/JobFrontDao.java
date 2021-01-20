package com.sfac.geniusdirecruit.modules.frontdesksystem.dao;

import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Job;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Resume;
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
public interface JobFrontDao {
    //将url增加到简历表中
    @Insert("insert into resume (url) values (#{url})")
    int insertByUrl(String url);

    @Select("select * from resume where resume_name=#{resumeName}")
    Resume findByResumeName(String s);
}
