package com.songyuankun.wechat.jd;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UnionJdProxyTest {

    @Autowired
    UnionJdProxy unionJdProxy;

    @Test
    void getCommand() {
        unionJdProxy.getCommand("https://item.m.jd.com/product/10050064328710.html?&utm_source=iosapp&utm_medium=appshare&utm_campaign=t_335139774&utm_term=CopyURL&ad_od=share&gx=RnFtxGMNbTbcydQQroxwWGyathw3zTQHwfQY");
    }
}