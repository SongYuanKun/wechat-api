package com.songyuankun.wechat.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProduceMqConfig {

    @Bean
    public DefaultMQProducer mqProducer() throws MQClientException {
        DefaultMQProducer mqProducer = new DefaultMQProducer();
        mqProducer.setNamesrvAddr("192.168.31.88:9876");
        mqProducer.setProducerGroup("DEFAULT_GROUP");
        mqProducer.start();
        return mqProducer;
    }

}

