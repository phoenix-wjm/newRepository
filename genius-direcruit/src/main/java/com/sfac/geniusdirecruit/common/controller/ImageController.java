package com.sfac.geniusdirecruit.common.controller;

import com.sfac.geniusdirecruit.common.service.ImageService;
import com.sfac.geniusdirecruit.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: yzs
 * @Date: 2021/1/12 9:26
 * 概要：
 * XXXXX
 */
@RestController
@RequestMapping("/api")
public class ImageController {

    @Autowired
    private ImageService imageService;

    /**
     * 127.0.0.1/api/image ---- post
     */
    @PostMapping(value = "/image", consumes = "multipart/form-data")
    public Result<String> uploadImage(@RequestParam MultipartFile image) {
        return imageService.uploadImage(image);
    }

}
