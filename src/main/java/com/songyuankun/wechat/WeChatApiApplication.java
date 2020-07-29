package com.songyuankun.wechat;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author songyuankun
 */
@SpringBootApplication
@NacosPropertySource(dataId = "wechat-api", autoRefreshed = true)
public class WeChatApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeChatApiApplication.class, args);
    }

}
