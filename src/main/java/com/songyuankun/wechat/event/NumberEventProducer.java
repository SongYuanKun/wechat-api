package com.songyuankun.wechat.event;

import com.lmax.disruptor.RingBuffer;

/**
 *
 * 阅读 点赞 事件生产者
 *
 * @author songyuankun
 */
public class NumberEventProducer {
    private final RingBuffer<NumberEvent> ringBuffer;

    public NumberEventProducer(RingBuffer<NumberEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void onData(Integer type, Integer id) {
        long sequence = ringBuffer.next();
        try {
            NumberEvent event = ringBuffer.get(sequence);
            // for the sequence
            event.setValue(id);
            event.setType(type);
        } finally {
            ringBuffer.publish(sequence);
        }
    }

}
