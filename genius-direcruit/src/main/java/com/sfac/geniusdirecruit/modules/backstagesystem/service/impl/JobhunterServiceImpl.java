package com.sfac.geniusdirecruit.modules.backstagesystem.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.dao.JobhunterDao;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Jobhunter;
import com.sfac.geniusdirecruit.modules.backstagesystem.service.JobhunterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:26
 * 概要：
 * XXXXX
 */
@Service
public class JobhunterServiceImpl implements JobhunterService {
    @Autowired
    private JobhunterDao jobhunterDao;


    //定义文件上传保存的路径
    @Value("${file.address}")
    private String fileAddres;

    @Value("${file.staticPath}")
    private String fileStaticPath;

    @Override
    public List<Jobhunter> getJobhunter() {

        return jobhunterDao.getJobhunter();
    }

    @Override
    public PageInfo<Jobhunter> getJobhunterBySearchBean(SearchBean searchBean) {
        searchBean.initSearchBean();
        PageHelper.startPage(searchBean.getCurrentPage(), searchBean.getPageSize());
        return new PageInfo<>(Optional
                .ofNullable(jobhunterDao.getJobhunterBySearchBean())
                .orElse(Collections.emptyList()));
    }


    @Override
    public ResultEntity uploadUserFile(MultipartFile file) {

        // 获取图片名称
        String fileFileName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + fileFileName.substring(fileFileName.lastIndexOf("."));

        // build 相对路劲，并存入数据库
        String url =fileStaticPath + newName;
        //将文件的相对路径插入到数据库中
//        int n =  jobhunterDao.insertByUrl(url);

        // build绝对路径，图片存入硬盘
        String path = fileAddres + newName;
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try {
            //如果存在就执行下面操作
            file.transferTo(dir);//将上传的实体文件复制到制定目录upload下
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultEntity<>(ResultEntity.ResultStatus.FAILED.status,"File upload error.");
        }

        return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,"File upload success.", url);


    }

}
