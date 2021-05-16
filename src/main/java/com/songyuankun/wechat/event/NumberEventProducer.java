package com.songyuankun.wechat.event;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * 阅读 点赞 事件生产者
 *
 * @author songyuankun
 */
@Component
public class NumberEventProducer {
    private final DefaultMQProducer mqProducer;

    public NumberEventProducer(DefaultMQProducer mqProducer) {
        this.mqProducer = mqProducer;
    }

    public void onData(Integer type, Integer id) {
        Message message = new Message();
        message.setBody(id.toString().getBytes(StandardCharsets.UTF_8));
        message.setTags(String.valueOf(type));
        message.setTopic("ARTICLE_INFO");
        try {
            mqProducer.send(message);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
