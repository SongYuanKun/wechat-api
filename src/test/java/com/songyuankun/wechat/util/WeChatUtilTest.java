package com.songyuankun.wechat.util;

import com.songyuankun.wechat.WeChatApiApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WeChatUtilTest extends WeChatApiApplicationTests {

    @Autowired
    private WeChatUtil weChatUtil;

    @Test
    public void addImageFromUrl() {
        String s = weChatUtil.addImageFromUrl(null);
        System.out.println(s);
    }
}