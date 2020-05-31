package com.songyuankun.wechat.util;

import com.songyuankun.wechat.WeChatApiApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WeChatUtilTest extends WeChatApiApplicationTests {

    @Autowired
    private WeChatUtil weChatUtil;

    @Test
    public void addImageFromUrl() {
        String s = weChatUtil.addThumbMedia("http://pic-oss.songyuankun.top/001988c1-e3a8-4f65-8d4a-2dbf72f3ab98.jpg");
        System.out.println(s);
    }
}