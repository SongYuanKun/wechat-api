package com.songyuankun.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.songyuankun.wechat.common.TokenCommon;
import com.songyuankun.wechat.common.WeChatCommon;
import com.songyuankun.wechat.entity.User;
import com.songyuankun.wechat.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author songyuankun
 */
@RestController
@Slf4j
public class LoginController {
    private final TokenCommon tokenCommon;
    private final UserRepository userRepository;
    private final WeChatCommon weChatCommon;


    public LoginController(TokenCommon tokenCommon, UserRepository userRepository, WeChatCommon weChatCommon) {
        this.tokenCommon = tokenCommon;
        this.userRepository = userRepository;
        this.weChatCommon = weChatCommon;
    }


    @PostMapping("/login")
    public Map<String, Object> doLogin(@RequestParam(value = "code", required = false) String code,
                                       @RequestParam(value = "rawData", required = false) String rawData,
                                       @RequestParam(value = "signature", required = false) String signature,
                                       @RequestParam(value = "encryptedData", required = false) String encryptedData,
                                       @RequestParam(value = "iv", required = false) String iv) {
        log.info("Start get SessionKey");

        Map<String, Object> map = new HashMap<>(16);
        log.info("用户非敏感信息" + rawData);

        JSONObject rawDataJson = JSONObject.parseObject(rawData);

        log.info("签名" + signature);
        JSONObject sessionKeyOpenId = weChatCommon.getSessionKeyOrOpenId(code);
        log.info("post请求获取的SessionAndOpenId=" + sessionKeyOpenId);

        String openid = sessionKeyOpenId.getString("openid");

        String sessionKey = sessionKeyOpenId.getString("session_key");

        log.info("openid=" + openid + ",session_key=" + sessionKey);

        User user = userRepository.findByUid(openid);
        //uuid生成唯一key
        if (user == null) {
            //入库
            String nickName = rawDataJson.getString("nickName");
            String avatarUrl = rawDataJson.getString("avatarUrl");
            String gender = rawDataJson.getString("gender");
            String city = rawDataJson.getString("city");
            String country = rawDataJson.getString("country");
            String province = rawDataJson.getString("province");

            user = new User();
            user.setUid(openid);
            user.setCreateTime(new Date());
            user.setSessionKey(sessionKey);
            user.setBalance(0);
            user.setAddress(country + " " + province + " " + city);
            user.setAvatar(avatarUrl);
            user.setGender(Integer.parseInt(gender));
            user.setUserName(nickName);
            user.setUpdateTime(new Date());
            userRepository.save(user);
        }
        JSONObject userInfo = WeChatCommon.getUserInfo(encryptedData, sessionKey, iv);
        if (userInfo != null) {
            log.info("根据解密算法获取的userInfo=" + userInfo);
            userInfo.put("balance", user.getBalance());
            map.put("userInfo", user);
        }

        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        String token = tokenCommon.getToken(openid, simpleGrantedAuthorities);
        map.put("token", "Bearer " + token);
        return map;
    }

    @GetMapping("queryUser")
    public User queryUser(Authentication authentication) {
        int userId = Integer.parseInt(authentication.getName());
        return userRepository.getOne(userId);
    }

    @GetMapping("check")
    public Map<String, Object> check() {
        HashMap<String, Object> map = new HashMap<>(2);
        map.put("status", true);
        return map;
    }


}
