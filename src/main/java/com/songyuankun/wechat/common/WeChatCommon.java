package com.songyuankun.wechat.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.songyuankun.wechat.util.HttpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author songyuankun
 */
@Component
@Slf4j
public class WeChatCommon {

    @Value("${my.wechat.appid}")
    private String appId;
    @Value("${my.wechat.secret}")
    private String secret;

    public JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
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
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidParameterSpecException |
                 IllegalBlockSizeException | BadPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | NoSuchProviderException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public JSONObject getSessionKeyOrOpenId(String code) {
        //微信端登录code
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        Map<String, String> requestUrlParam = new HashMap<>(16);
        requestUrlParam.put("appid", appId);
        requestUrlParam.put("secret", secret);
        requestUrlParam.put("js_code", code);
        requestUrlParam.put("grant_type", "authorization_code");

        //发送post请求读取调用微信接口获取openid用户唯一标识
        return JSON.parseObject(HttpsUtil.sendGet(requestUrl, requestUrlParam));
    }


}
