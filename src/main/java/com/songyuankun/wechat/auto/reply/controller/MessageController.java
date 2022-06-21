package com.songyuankun.wechat.auto.reply.controller;

import com.alibaba.fastjson.JSONObject;
import com.songyuankun.wechat.auto.reply.dto.MessageDTO;
import com.songyuankun.wechat.auto.reply.service.WeChatService;
import com.songyuankun.wechat.jd.UnionJDProxy;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "weixin")
@RestController
@Slf4j
@RequestMapping("weixin")
public class MessageController {

    @Autowired
    private WeChatService weChatService;
    @Autowired
    private UnionJDProxy unionJDProxy;

    @PostMapping(value = "auto-reply", consumes = "text/xml", produces = "text/xml")
    public MessageDTO autoReplay(@RequestBody MessageDTO messageDTO) {
        log.info("messageDTO:{}", messageDTO);
        String url = unionJDProxy.getCommand(messageDTO.getContent());
        JSONObject goodsInfo = unionJDProxy.getGoodsInfo(messageDTO.getContent());
        if (goodsInfo == null || url == null) {
            return null;
        }
        String command = "商品名称：" + goodsInfo.getString("goodsName") + "\r\n" +
                "价格：" + goodsInfo.getString("unitPrice") + "\r\n" +
                "返佣比例：" + goodsInfo.getString("commisionRatioPc") + "%\r\n" +
                "预计返佣：" + goodsInfo.getInteger("unitPrice") * goodsInfo.getInteger("commisionRatioPc") * 0.01 + "\r\n" +
                "下单地址：" + url +
                "";
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
