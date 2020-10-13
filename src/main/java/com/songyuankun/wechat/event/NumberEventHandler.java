package com.songyuankun.wechat.event;

import com.alibaba.fastjson.JSON;
import com.lmax.disruptor.EventHandler;
import com.songyuankun.wechat.service.ArticleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * * 阅读 点赞 事件消费者
 *
 * @author songyuankun
 */
@Slf4j
@Service
public class NumberEventHandler implements EventHandler<NumberEvent> {

    private final ArticleServiceImpl articleServiceImpl;

    public NumberEventHandler(ArticleServiceImpl articleServiceImpl) {
        this.articleServiceImpl = articleServiceImpl;
    }

    @Override
    public void onEvent(NumberEvent event, long sequence, boolean endOfBatch) {
        log.info("Event:  " + JSON.toJSONString(event) + "sequence:+" + sequence + "  endOfBatch:" + endOfBatch);
        switch (event.getType()) {
            case 1:
                articleServiceImpl.updateReadNum(event.getValue());
                break;
            case 2:
                articleServiceImpl.updateLikeNum(event.getValue());
                break;
            default:
                break;
        }
    }
}