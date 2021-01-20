package com.sfac.geniusdirecruit.modules.backstagesystem.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public class MessageVo {
    private Integer messageId;
    //用户名
    private String userName;

    //private Integer userId;

    //职位名称
    private String jobName;


    //private Integer jobId;

    //留言内容
    private String content;

    //留言标题
    private String title;

    //留言时间
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private LocalDateTime messageTime;
}
