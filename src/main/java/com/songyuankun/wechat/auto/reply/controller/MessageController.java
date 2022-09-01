package com.songyuankun.wechat.auto.reply.controller;

import com.alibaba.fastjson.JSONObject;
import com.songyuankun.wechat.auto.reply.dto.MessageDTO;
import com.songyuankun.wechat.auto.reply.service.WeChatService;
import com.songyuankun.wechat.jd.UnionJdProxy;
import com.songyuankun.wechat.taobao.UnionTaoBaoProxy;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author songyuankun
 */
@Api(value = "weixin")
@RestController
@Slf4j
@RequestMapping("weixin")
public class MessageController {

    @Autowired
    private WeChatService weChatService;
    @Autowired
    private UnionJdProxy unionJdProxy;
    @Autowired
    private UnionTaoBaoProxy unionTaoBaoProxy;

    @PostMapping(value = "auto-reply", consumes = "text/xml", produces = "text/xml")
    public MessageDTO autoReplay(@RequestBody MessageDTO messageDTO) {
        log.info("messageDTO:{}", messageDTO);
        String command = unionJdProxy.getGoodsInfo(messageDTO.getContent());
        if (StringUtils.isBlank(command)){
            unionTaoBaoProxy.getCommand(messageDTO.getContent());
        }
        return messageDTO.replay(command);
    }

    @GetMapping("auto-reply")
    public String autoReplay(@RequestParam String signature, @RequestParam String timestamp, @RequestParam String nonce, @RequestParam String echostr) {
        log.info("signature:{},timestamp:{},nonce:{},echostr:{}", signature, timestamp, nonce, echostr);
        if (weChatService.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        } else {
            return "";
        }
    }

}
