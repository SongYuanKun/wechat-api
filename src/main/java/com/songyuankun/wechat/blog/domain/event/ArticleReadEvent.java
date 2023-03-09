package com.songyuankun.wechat.blog.domain.event;

import com.songyuankun.wechat.common.DomainEvent;

import org.apache.rocketmq.common.message.Message;

import java.time.LocalDateTime;


public class ArticleReadEvent extends DomainEvent<ArticleReadEvent.ArticleReadEventPayload> {

    public ArticleReadEvent(String eventName, LocalDateTime occurredOn, ArticleReadEventPayload eventData) {
        super(eventName, occurredOn, eventData);
    }

    @Override
    public Message toRocketMQMessage() {
        return null;
    }

    public static class ArticleReadEventPayload {
        private String orderId;
        private double paymentAmount;
        private String buyerName;

        // getters and setters
    }
}
