package com.songyuankun.wechat.listener;

import com.songyuankun.wechat.service.ArticleServiceImpl;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 博客相关的MQ
 *
 * @author songyuankun
 */
@Component
@RocketMQMessageListener(topic = "ARTICLE_INFO", consumerGroup = "article-read", selectorExpression = "READ")
public class ArticleReadListener implements RocketMQListener<String> {

    private final ArticleServiceImpl articleService;

    public ArticleReadListener(ArticleServiceImpl articleService) {
        this.articleService = articleService;
    }


    @Override
    public void onMessage(String s) {
        articleService.updateReadNum(Integer.valueOf(s));
    }
}
