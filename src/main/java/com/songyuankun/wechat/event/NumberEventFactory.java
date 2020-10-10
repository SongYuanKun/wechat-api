package com.songyuankun.wechat.event;

import com.lmax.disruptor.EventFactory;

/**
 *  阅读 点赞 事件工厂
 * @author songyuankun
 */
public class NumberEventFactory implements EventFactory<NumberEvent> {
    @Override
    public NumberEvent newInstance() {
        return new NumberEvent();
    }
}
