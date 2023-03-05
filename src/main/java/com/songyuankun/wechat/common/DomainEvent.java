package com.songyuankun.wechat.common;

import org.apache.rocketmq.common.message.Message;

import java.time.LocalDateTime;

public abstract class DomainEvent<T> {
    private String eventName;
    private LocalDateTime occurredOn;
    private T eventData;

    public DomainEvent(String eventName, LocalDateTime occurredOn, T eventData) {
        this.eventName = eventName;
        this.occurredOn = occurredOn;
        this.eventData = eventData;
    }

    public String getEventName() {
        return eventName;
    }

    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }

    public T getEventData() {
        return eventData;
    }

    /**
     * 定义子类必须实现的方法，用于将领域事件转化为RocketMQ消息
     *
     * @return Message
     */
    public abstract Message toRocketMQMessage();
}
