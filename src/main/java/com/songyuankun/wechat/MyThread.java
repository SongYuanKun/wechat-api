package com.songyuankun.wechat;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class MyThread {


    private final RestTemplate restTemplate;

    public MyThread(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CompletableFuture<String> get() {
        try {
            String url = "http://manage.huohua.cn/material/api/";
            ResponseEntity<JSONObject> results = restTemplate.getForEntity(url, JSONObject.class);
            HttpStatus statusCode = results.getStatusCode();
            if (statusCode != HttpStatus.OK) {
                log.info(Objects.requireNonNull(results.getBody()).toJSONString());
            }
        } catch (Exception e) {
            log.info("error", e);
        }

        return CompletableFuture.completedFuture("");
    }
}
