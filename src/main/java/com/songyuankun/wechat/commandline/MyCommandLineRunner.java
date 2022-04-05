package com.songyuankun.wechat.commandline;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * MQ初始化操作
 *
 * @author SongYuanKun
 */
@Component
@Slf4j
public class MyCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        log.info("my command line runner start...");
    }
}