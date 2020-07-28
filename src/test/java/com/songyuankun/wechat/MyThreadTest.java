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
    //5个线程 执行100000次
    @PerfTest(invocations = 1000, threads = 100)
    public void test() {
        myThread.get();
    }

}