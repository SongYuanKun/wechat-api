package com.songyuankun.wechat.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author songyuankun
 */
@Component
public class WeChatUtil {

    private final RedisUtil redisUtil;

    public WeChatUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    private static String getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token";
        HashMap<String, String> parameters = new HashMap<>(4);
        parameters.put("grant_type", "client_credential");
        parameters.put("appid", "wx831e55a6a77b90b7");
        parameters.put("secret", "3ac3104e959efe7431115ed7979997cf");
        String s = HttpsUtil.sendGet(url, parameters);
        JSONObject jsonObject = JSON.parseObject(s);
        return jsonObject.getString("access_token");
    }


    public String getAccessTokenFromRedis() {
        String accessToken = redisUtil.get("WE_CHAT_TOKEN");
        if (accessToken == null) {
            accessToken = getAccessToken();
            if (accessToken != null) {
                redisUtil.setString("WE_CHAT_TOKEN", accessToken, 7200);
            }
        }
        return accessToken;
    }



}
