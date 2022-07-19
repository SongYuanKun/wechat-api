package com.songyuankun.wechat.auto.reply.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class QyWeChatMessageDTO {
    @JsonProperty(value = "touser")
    private String toUser;
    @JsonProperty(value = "msgtype")
    private String msgType;
    @JsonProperty(value = "agentid")
    private Integer agentId;
    private String content;
    private Integer safe;
}
