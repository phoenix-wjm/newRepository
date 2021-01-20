package com.sfac.geniusdirecruit.common.service;

import com.sfac.geniusdirecruit.common.vo.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: yzs
 * @Date: 2021/1/11 20:19
 * 概要：
 * XXXXX
 */
public interface ImageService {
    Result<String> uploadImage(MultipartFile image);
}
