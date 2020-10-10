package com.songyuankun.wechat;

import lombok.extern.slf4j.Slf4j;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;


@Slf4j
public class MyThreadTest extends WeChatApiApplicationTests {

    @Rule
    public ContiPerfRule contiPerfRule = new ContiPerfRule();

    @Test
    @PerfTest(invocations = 1000, threads = 20)
    public void test() {
        log.info("ok");
        Assert.assertTrue(true);
    }

}