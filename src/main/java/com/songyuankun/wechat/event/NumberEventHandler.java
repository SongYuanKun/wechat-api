package com.songyuankun.wechat.event;

import com.alibaba.fastjson.JSON;
import com.lmax.disruptor.EventHandler;
import com.songyuankun.wechat.service.ArticleServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.Objects;

/**
 *  * 阅读 点赞 事件消费者
 *
 * @author songyuankun
 */
@Slf4j
public class NumberEventHandler implements EventHandler<NumberEvent> {

    @Override
    public void onEvent(NumberEvent event, long sequence, boolean endOfBatch) {
        log.info("Event:  " + JSON.toJSONString(event) + "sequence:+" + sequence + "  endOfBatch:" + endOfBatch);
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        ArticleServiceImpl articleServiceImpl = Objects.requireNonNull(wac).getBean("articleServiceImpl", ArticleServiceImpl.class);
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