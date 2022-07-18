package com.songyuankun.wechat.auto.reply.service;

import com.songyuankun.wechat.auto.reply.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author songyuankun
 */
@Service
public class AdminService {


    @Value("${my.wechat.id}")
    String id;

    @Value("${my.wechat.admin}")
    String admin;


    public MessageDTO sendMessageToAdmin(String content) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setContent(content);
        messageDTO.setCreateTime(System.currentTimeMillis() / 1000);
        messageDTO.setFromUserName(id);
        messageDTO.setToUserName(admin);
        messageDTO.setMsgType("text");
        return messageDTO;
    }

}
