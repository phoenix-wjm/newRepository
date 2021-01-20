package com.sfac.geniusdirecruit.modules.backstagesystem.service;

import com.github.pagehelper.PageInfo;
import com.sfac.geniusdirecruit.common.entity.ResultEntity;
import com.sfac.geniusdirecruit.common.entity.SearchBean;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.Message;
import com.sfac.geniusdirecruit.modules.backstagesystem.entity.vo.MessageVo;

import java.util.List;

/**
 * @Author: yzs
 * @Date: 2020/12/31 16:27
 * 概要：
 * XXXXX
 */
public interface MessageService {
    ResultEntity<MessageVo> addMessage(MessageVo messageVo);

    ResultEntity<Object> deleteMessageById(Integer leaveWordId);

    List<Message> getMessage();

    PageInfo<Message> getMessageBySearchBean(SearchBean searchBean);


    PageInfo<MessageVo> getMessageVoBySearchBean(SearchBean searchBean);
}
