package com.sfac.geniusdirecruit.common.service.impl;

import java.io.File;
import java.io.IOException;

import com.sfac.geniusdirecruit.common.service.ImageService;
import com.sfac.geniusdirecruit.common.utile.FileUtil;
import com.sfac.geniusdirecruit.common.vo.Result;
import com.sfac.geniusdirecruit.common.vo.Result.ResultStatus;
import com.sfac.geniusdirecruit.config.web.ResourceConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



/**
 * @Author: yzs
 * @Date: 2021/1/11 20:19
 * 概要：
 * XXXXX
 */
@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ResourceConfigBean resourceConfigBean;

    @Override
    public Result<String> uploadImage(MultipartFile image) {
        if (image.isEmpty()) {
            return new Result<>(ResultStatus.FAILED.status, "User image is empty.");
        }
        if (!FileUtil.isImage(image)) {
            return new Result<>(ResultStatus.FAILED.status, "File is not a image.");
        }

        File destFolder = new File(resourceConfigBean.getLocalPathForWindow());
        if (!destFolder.exists()) {
            destFolder.mkdirs();
        }

        String originalFilename = image.getOriginalFilename();
        String relatedPath = resourceConfigBean.getResourcePath() + originalFilename;
        String destPath = String.format("%s%s", resourceConfigBean.getLocalPathForWindow(), originalFilename);

        try {
            File destFile = new File(destPath);
            image.transferTo(destFile);
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return new Result<>(ResultStatus.FAILED.status, "File upload error.");
        }

        return new Result<>(ResultStatus.SUCCESS.status, "File upload success.", relatedPath);
    }
}
