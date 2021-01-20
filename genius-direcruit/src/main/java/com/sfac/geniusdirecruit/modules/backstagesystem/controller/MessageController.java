package com.sfac.geniusdirecruit.modules.backstagesystem.controller;

import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Message;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.vo.MessageVo;
import com.sfac.geniusdirecruit.modules.backstagesystem.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:11
 * 概要：
 * XXXXX
 */

@RestController
@RequestMapping("/api")
public class MessageController {
    @Autowired
    private MessageService messageService;

    //添加message http://127.0.0.1:8080/api/message
    //{"userId":1, "jobId":2, "content":"message_id", "title":"asc", "messageTime":""}
    @PostMapping(value = "/message", consumes = "application/json")
    public ResultEntity<MessageVo> insertLeaveWord(@RequestBody MessageVo messageVo) {
        return messageService.addMessage(messageVo);
    }

    //删除message http://127.0.0.1:8080/api/message/1
    @DeleteMapping("/message/{messageId}")
    public ResultEntity<Object> deleteLeaveWordById(@PathVariable("messageId") Integer messageId) {
        return messageService.deleteMessageById(messageId);
    }

    //查询message
    //http://127.0.0.1:8080/api/messages  get请求
    @GetMapping("/messages")
    public List<Message> messages() {
        return messageService.getMessage();
    }
    //分页查询message
    //http://127.0.0.1:8080/api/messages   post请求
    // {"currentPage":3, "pageSize":2, "order":"create_time", "direction":"desc", "keyWord":""}
    @PostMapping(value = "/messages", consumes = "application/json")
    public PageInfo<Message> getMessagesBySearchBean(
            @RequestBody SearchBean searchBean) {
        return messageService.getMessageBySearchBean(searchBean);
    }


    //分页查询messageVo
    //http://127.0.0.1:8080/api/messages   post请求
    // {"currentPage":3, "pageSize":2, "order":"create_time", "direction":"desc", "keyWord":""}
    @PostMapping(value = "/messageVoes", consumes = "application/json")
    public PageInfo<MessageVo> getMessagVoesBySearchBean(
            @RequestBody SearchBean searchBean) {
        return messageService.getMessageVoBySearchBean(searchBean);
    }
}
