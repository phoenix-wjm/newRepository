package com.sfac.geniusdirecruit.modules.frontdesksystem.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Job;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Resume;
import com.sfac.geniusdirecruit.modules.frontdesksystem.dao.JobFrontDao;
import com.sfac.geniusdirecruit.modules.frontdesksystem.dao.JobsFrontDao;
import com.sfac.geniusdirecruit.modules.frontdesksystem.dao.JobsMybatisDao;
import com.sfac.geniusdirecruit.modules.frontdesksystem.service.JobFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.net.URLEncoder;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:25
 * 概要：
 * XXXXX
 */
@Service
public class JobFrontServiceImpl implements JobFrontService {
    @Autowired
    private JobFrontDao jobFrontDao;
    @Autowired
    private JobsFrontDao jobsFrontDao;
    @Autowired
    private JobsMybatisDao jobsMybatisDao;
    @Autowired
    private RedisTemplate redisTemplate;

    //定义文件上传保存的路径
    @Value("${file.address}")
    private String fileAddres;

    @Value("${file.staticPath}")
    private String fileStaticPath;

    @Override
    public HashMap<String, Object> findAll(int currentPage) {
        HashMap<String, Object> map = new HashMap();

        Job job = new Job();
        job.getRow();
        job.setPage(currentPage);
//        Pageable pageable = PageRequest.of(job.getPage() - 1, job.getRow(), Sort.by(new String[]{"numbers"}).descending());
//        Page<Job> userInfoPage = jobsFrontDao.findAll(pageable);
        PageHelper.startPage(currentPage, 5);
        PageInfo<Job> pageInfo = new PageInfo<>(jobsMybatisDao.findAll());
        map.put("curPage", pageInfo.getPageNum());
        if (pageInfo.getPageNum() <= 1){
            map.put("prePage",pageInfo.getPageNum());
        }else {
            map.put("prePage",pageInfo.getPageNum()-1);
        }
        if (pageInfo.getPageNum()+1 >= pageInfo.getPages()){
            map.put("nextPage",pageInfo.getPages());
        }else {
            map.put("nextPage",pageInfo.getPageNum()+1);
        }
        map.put("totalPages",pageInfo.getPages());
        map.put("totalElements",pageInfo.getTotal());
        map.put("list",pageInfo.getList());
//        map.put("curPage", userInfoPage.getNumber() + 1);
//        if (userInfoPage.getNumber() < 1) {
//            map.put("prePage", userInfoPage.getNumber() + 1);
//        } else {
//            map.put("prePage", userInfoPage.getNumber());
//        }
//
//        if (userInfoPage.getNumber() + 2 >= userInfoPage.getTotalPages()) {
//            map.put("nextPage", userInfoPage.getTotalPages());
//        } else {
//            map.put("nextPage", userInfoPage.getNumber() + 2);
//        }
//
//        map.put("totalPages", userInfoPage.getTotalPages());
//        map.put("totalElements", userInfoPage.getTotalElements());
//        map.put("list", userInfoPage.getContent());
        return map;
    }

    //将过ID和名字查询用户的简历
    @Override
    public Resume findByResumeName() {
        //测试
        String s= "杨尚斌";
        return jobFrontDao.findByResumeName(s);
    }

    @Override
    public ResultEntity uploadUserFile(MultipartFile file) {
        //表示文件上传后的目标文件路径
        String path = fileAddres + "/" + fileStaticPath;
        //获取文件名称
        String fileFileName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + fileFileName.substring(fileFileName.lastIndexOf("."));
        //获取文件的相对路径
        String url =fileStaticPath+"/"+newName;
        //将文件的相对路径插入到数据库中
        int n =  jobFrontDao.insertByUrl(url);
        //创建File对象，传入目标路径参数和文件名称
        File dir = new File(path, newName);
        //如果dir代表的文件不存在，则创建
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
        return new ResultEntity<>(ResultEntity.ResultStatus.SUCCESS.status,"File upload success.",fileFileName);
    }

    @Override
    public ResultEntity downloadUserFile(String fileName, HttpServletResponse response)  throws IOException {
        //获取需要下载的文件的路径
        String path = fileAddres+ "/"+fileName;
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(path)));
        //转码，免得文件名中文乱码
        fileName = URLEncoder.encode(fileName, "UTF-8");
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while ((len = bis.read()) != -1) {
            out.write(len);
            out.flush();
        }
        out.close();
        return new ResultEntity<>(ResultEntity.ResultStatus.FAILED.status,"File download success.",fileName);
    }

    @Override
    public HashMap<String, Object> findBySearch(int page, String search, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap();
        if (search!="" && search.charAt(0)!='%'){
        redisTemplate.opsForValue().increment(search,1);

        Job job = new Job();
        job.getRow();
        job.setPage(page);
        PageHelper.startPage(page, 5);
        String str = "%"+search+"%";
        PageInfo<Job> pageInfo = new PageInfo<>(jobsMybatisDao.findBySearch(str));
        map.put("curPage", pageInfo.getPageNum());
        if (pageInfo.getPageNum() <= 1){
            map.put("prePage",pageInfo.getPageNum());
        }else {
            map.put("prePage",pageInfo.getPageNum()-1);
        }
        if (pageInfo.getPageNum()+1 >= pageInfo.getPages()){
            map.put("nextPage",pageInfo.getPages());
        }else {
            map.put("nextPage",pageInfo.getPageNum()+1);
        }
        map.put("totalPages",pageInfo.getPages());
        map.put("totalElements",pageInfo.getTotal());
        map.put("list",pageInfo.getList());
        request.getSession().setAttribute("search",str);
        }else {
            Job job = new Job();
            job.getRow();
            job.setPage(page);
            PageHelper.startPage(page, 5);
            String str = "%"+search+"%";
            PageInfo<Job> pageInfo = new PageInfo<>(jobsMybatisDao.findBySearch(str));
            map.put("curPage", pageInfo.getPageNum());
            if (pageInfo.getPageNum() <= 1){
                map.put("prePage",pageInfo.getPageNum());
            }else {
                map.put("prePage",pageInfo.getPageNum()-1);
            }
            if (pageInfo.getPageNum()+1 >= pageInfo.getPages()){
                map.put("nextPage",pageInfo.getPages());
            }else {
                map.put("nextPage",pageInfo.getPageNum()+1);
            }
            map.put("totalPages",pageInfo.getPages());
            map.put("totalElements",pageInfo.getTotal());
            map.put("list",pageInfo.getList());
            request.getSession().setAttribute("search",str);
        }
        return map;
    }
}


