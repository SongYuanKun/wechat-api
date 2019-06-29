package com.songyuankun.wechat.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.songyuankun.wechat.dao.User;
import com.songyuankun.wechat.repository.UserRepository;
import com.songyuankun.wechat.util.HttpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.*;

/**
 * @author songyuankun
 */
@RestController
@Slf4j
public class LoginController {
    private final UserRepository userRepository;
    @Value("${my.wechat.appid}")
    private String appId;
    @Value("${my.wechat.secret}")
    private String secret;

    public LoginController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private static JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
        // 被加密的数据
        byte[] dataByte = Base64.getDecoder().decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.getDecoder().decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.getDecoder().decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + 1;
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, StandardCharsets.UTF_8);
                return JSON.parseObject(result);
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidParameterSpecException | IllegalBlockSizeException | BadPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | NoSuchProviderException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private JSONObject getSessionKeyOrOpenId(String code) {
        //微信端登录code
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> requestUrlParam = new HashMap<>(16);
        requestUrlParam.put("appid", appId);
        requestUrlParam.put("secret", secret);
        requestUrlParam.put("js_code", code);
        requestUrlParam.put("grant_type", "authorization_code");

        //发送post请求读取调用微信接口获取openid用户唯一标识
        return JSONObject.parseObject(HttpsUtil.sendGet(requestUrl, requestUrlParam));
    }

    @GetMapping("/login")
    public Map<String, Object> doLogin(@RequestParam(value = "code", required = false) String code,
                                       @RequestParam(value = "rawData", required = false) String rawData,
                                       @RequestParam(value = "signature", required = false) String signature,
                                       @RequestParam(value = "encryptedData", required = false) String encryptedData,
                                       @RequestParam(value = "iv", required = false) String iv) {
        log.info("Start get SessionKey");

        Map<String, Object> map = new HashMap<>();
        log.info("用户非敏感信息" + rawData);

        JSONObject rawDataJson = JSONObject.parseObject(rawData);

        log.info("签名" + signature);
        JSONObject sessionKeyOpenId = getSessionKeyOrOpenId(code);
        log.info("post请求获取的SessionAndOpenId=" + sessionKeyOpenId);

        String openid = sessionKeyOpenId.getString("openid");

        String sessionKey = sessionKeyOpenId.getString("session_key");

        log.info("openid=" + openid + ",session_key=" + sessionKey);

        User user = userRepository.findByUid(openid);
        //uuid生成唯一key
        String key = UUID.randomUUID().toString();
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
            user.setUuidKey(key);
            user.setAddress(country + " " + province + " " + city);
            user.setAvatar(avatarUrl);
            user.setGender(Integer.parseInt(gender));
            user.setUserName(nickName);
            user.setUpdateTime(new Date());
            userRepository.save(user);
        } else {
            // 重新设置会话key
            int i = this.userRepository.updateUuidKeyByUid(key, openid);
            if (i > 0) {
                log.info("key更新成功");
            }
        }

        map.put("key", key);
        map.put("result", "0");


        JSONObject userInfo = getUserInfo(encryptedData, sessionKey, iv);
        if (userInfo != null) {
            log.info("根据解密算法获取的userInfo=" + userInfo);
            userInfo.put("balance", user.getBalance());
            map.put("userInfo", userInfo);
        }
        return map;
    }
}
