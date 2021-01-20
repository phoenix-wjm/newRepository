package com.sfac.geniusdirecruit.modules.backstagesystem.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:18
 * 概要：
 * XXXXX
 */
@Repository
@Mapper
public interface ResumeDao {
    @Delete("delete from resume where Job_hunter_id = #{JobhunterId}")
    void deleteResumesByJobhunterId(Integer jobHunterId);
}
