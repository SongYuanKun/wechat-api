package com.songyuankun.wechat.blog.domain.event;

import com.songyuankun.wechat.common.DomainEvent;
import org.apache.rocketmq.common.message.Message;

import java.time.LocalDateTime;


public class ArticleLikeEvent extends DomainEvent<ArticleLikeEvent.ArticleLikeEventPayload> {

    public ArticleLikeEvent(String eventName, LocalDateTime occurredOn, ArticleLikeEventPayload eventData) {
        super(eventName, occurredOn, eventData);
    }

    @Override
    public Message toRocketMQMessage() {
        return null;
    }

    public static class ArticleLikeEventPayload {
        private String orderId;
        private double paymentAmount;
        private String buyerName;

        // getters and setters
    }
}
