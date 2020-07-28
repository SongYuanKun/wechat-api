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

    public void get() {
            String url = "http://10.251.34.200:8080/resource/getFileList?classroom=MK2007232201";
            ResponseEntity<JSONObject> results = restTemplate.getForEntity(url, JSONObject.class);
            HttpStatus statusCode = results.getStatusCode();
            if (statusCode != HttpStatus.OK) {
                log.info(Objects.requireNonNull(results.getBody()).toJSONString());
            }
    }
}
