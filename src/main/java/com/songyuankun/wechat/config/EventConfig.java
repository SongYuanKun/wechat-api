package com.songyuankun.wechat.config;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import com.songyuankun.wechat.event.NumberEvent;
import com.songyuankun.wechat.event.NumberEventFactory;
import com.songyuankun.wechat.event.NumberEventHandler;
import com.songyuankun.wechat.event.NumberEventProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * 队列bean
 *
 * @author songyuankun
 */
@Configuration
public class EventConfig {
    private final NumberEventHandler numberEventHandler;

    public EventConfig(NumberEventHandler numberEventHandler) {
        this.numberEventHandler = numberEventHandler;
    }

    @Bean
    public NumberEventProducer numberEventProducer() {
        // The factory for the event
        NumberEventFactory factory = new NumberEventFactory();

        // Specify the size of the ring buffer, must be power of 2.
        int bufferSize = 1024;

        // Construct the Disruptor
        Disruptor<NumberEvent> disruptor = new Disruptor<>(factory, bufferSize, DaemonThreadFactory.INSTANCE);

        // Connect the handler
        disruptor.handleEventsWith(numberEventHandler);

        // Start the Disruptor, starts all threads running
        disruptor.start();

        // Get the ring buffer from the Disruptor to be used for publishing.
        RingBuffer<NumberEvent> ringBuffer = disruptor.getRingBuffer();

        return new NumberEventProducer(ringBuffer);
    }
}
