package com.songyuankun.wechat.blog.event;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

/**
 * 阅读 点赞 事件生产者
 *
 * @author songyuankun
 */
@Component
public class NumberEventProducer {
    private final RocketMQTemplate rocketMQTemplate;

    public NumberEventProducer(RocketMQTemplate rocketMQTemplate) {
        this.rocketMQTemplate = rocketMQTemplate;
    }

    public void onData(String type, Integer id) {
        rocketMQTemplate.asyncSend("ARTICLE_INFO:" + type, String.valueOf(id), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            @Override
            public void onException(Throwable throwable) {

            }
        });
    }

}
