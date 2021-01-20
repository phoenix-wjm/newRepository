package com.sfac.geniusdirecruit.modules.frontdesksystem.service;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Resume;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:25
 * 概要：
 * XXXXX
 */
public interface JobFrontService {
    HashMap<String, Object> findAll(int currentPage);

    Resume findByResumeName();

    ResultEntity uploadUserFile(MultipartFile file);

    ResultEntity downloadUserFile(String fileName, HttpServletResponse response) throws IOException;


    HashMap<String, Object> findBySearch(int page, String search, HttpServletRequest request);

}
