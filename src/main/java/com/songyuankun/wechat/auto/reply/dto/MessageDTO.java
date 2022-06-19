package com.songyuankun.wechat.auto.reply.dto;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MessageDTO {
    /**
     * 开发者微信号
     */
    private String toUserName;
    /**
     * 发送方帐号（一个OpenID）
     */
    private String fromUserName;
    /**
     * 消息创建时间 （整型）
     */
    private String createTime;
    /**
     * 消息类型，文本为text
     */
    private String msgType;
    /**
     * 文本消息内容
     */
    private String content;
    /**
     * 消息id，64位整型
     */
    private String msgId;
    /**
     * 消息的数据ID（消息如果来自文章时才有）
     */
    private String msgDataId;
    /**
     * 多图文时第几篇文章，从1开始（消息如果来自文章时才有）
     */
    private String idx;

    private  String signature;
    private  String nonce;
    private  String echostr;
}
