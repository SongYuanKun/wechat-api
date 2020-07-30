package com.songyuankun.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author songyuankun
 */
@SpringBootApplication
@EnableDiscoveryClient
public class WeChatApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(WeChatApiApplication.class, args);
    }
}
