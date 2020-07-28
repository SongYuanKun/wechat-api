package com.songyuankun.wechat;

import lombok.extern.slf4j.Slf4j;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class MyThreadTest extends WeChatApiApplicationTests {

    @Rule
    public ContiPerfRule contiPerfRule = new ContiPerfRule();
    @Autowired
    private MyThread myThread;

    @Test
    @PerfTest(invocations = 1000, threads = 20)
    public void test() {
        myThread.get();
    }

}