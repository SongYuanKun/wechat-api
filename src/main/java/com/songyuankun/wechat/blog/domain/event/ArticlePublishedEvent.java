package com.songyuankun.wechat.blog.domain.event;

import com.songyuankun.wechat.common.DomainEvent;

import org.apache.rocketmq.common.message.Message;

import java.time.LocalDateTime;

public class ArticlePublishedEvent extends DomainEvent<ArticlePublishedEvent.ArticlePublishedEventPayload> {
    public ArticlePublishedEvent(String eventName, LocalDateTime occurredOn, ArticlePublishedEventPayload eventData) {
        super(eventName, occurredOn, eventData);
    }

    @Override
    public Message toRocketMQMessage() {
        return null;
    }

    public class ArticlePublishedEventPayload {

        private Integer articleId;

        private String publishedTo;

    }


}
