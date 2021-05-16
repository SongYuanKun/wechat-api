package com.songyuankun.wechat.listener;

import com.songyuankun.wechat.enums.PvEventEnum;
import com.songyuankun.wechat.service.ArticleServiceImpl;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 博客相关的MQ
 *
 * @author songyuankun
 */
@Component
public class ArticleInfoListener implements MessageListenerConcurrently {

    private final ArticleServiceImpl articleService;

    public ArticleInfoListener(ArticleServiceImpl articleService) {
        this.articleService = articleService;
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        for (MessageExt messageExt : list) {
            String body = new String(messageExt.getBody(), StandardCharsets.UTF_8);
            String tags = messageExt.getTags();
            PvEventEnum pvEventEnum = PvEventEnum.getPvEventEnum(Integer.valueOf(tags));
            if (pvEventEnum == null) {
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            switch (pvEventEnum) {
                case LIKE:
                    articleService.updateLikeNum(Integer.valueOf(body));
                    break;
                case READ:
                    articleService.updateReadNum(Integer.valueOf(body));
                    break;
                default:
                    break;
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
