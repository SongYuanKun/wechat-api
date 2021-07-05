package com.songyuankun.wechat.commandline;

import com.songyuankun.wechat.listener.ArticleInfoListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * MQ初始化操作
 *
 * @author SongYuanKun
 */
@Component
@Slf4j
public class MyCommandLineRunner implements CommandLineRunner {

    private final ArticleInfoListener articleInfoListener;

    public MyCommandLineRunner(ArticleInfoListener articleInfoListener) {
        this.articleInfoListener = articleInfoListener;
    }

    @Override
    public void run(String... args) throws Exception {
        DefaultMQPushConsumer mqPushConsumer = new DefaultMQPushConsumer("ARTICLE_PV_GROUP");
        mqPushConsumer.setNamesrvAddr("192.168.31.88:9876");
        mqPushConsumer.subscribe("ARTICLE_INFO", "*");
        mqPushConsumer.registerMessageListener(articleInfoListener);
        mqPushConsumer.start();
    }
}