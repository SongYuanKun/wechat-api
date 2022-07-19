package com.songyuankun.wechat.auto.reply.service;

import com.songyuankun.wechat.auto.reply.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author songyuankun
 */
@Service
public class AdminService {


    @Value("${my.wechat.admin.agent_id}")
    String agentId;

    @Value("${my.wechat.admin.secret}")
    String secret;


    public void sendMessageToAdmin(String content) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setContent(content);
        messageDTO.setCreateTime(System.currentTimeMillis() / 1000);
        messageDTO.setMsgType("text");
    }

}
