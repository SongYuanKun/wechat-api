package com.songyuankun.wechat.service;

import com.google.code.kaptcha.Producer;
import com.songyuankun.wechat.constant.RedisKeyConstants;
import com.songyuankun.wechat.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

/**
 * @author songyuankun
 */
@Service
public class SysCaptchaServiceImpl {

    /**
     * 验证码过期时长5秒
     */
    private static final long CAPTCHA_EXPIRE = 300;
    private final Producer producer;
    private final RedisUtil redisUtil;

    public SysCaptchaServiceImpl(Producer producer, RedisUtil redisUtil) {
        this.producer = producer;
        this.redisUtil = redisUtil;
    }

    /**
     * 获取验证码
     */

    public BufferedImage getCaptcha(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            return null;
        }
        //生成文字验证码
        String code = producer.createText();
        // 存进redis,5分钟后过期
        redisUtil.setString(genRedisKey(uuid), code, CAPTCHA_EXPIRE);
        return producer.createImage(code);
    }

    /**
     * 验证验证码
     */

    public boolean validate(String uuid, String code) {
        if (StringUtils.isEmpty(uuid) || StringUtils.isEmpty(code)) {
            return false;
        }
        // 从redis中取
        String redisKey = genRedisKey(uuid);
        String captchaCode = redisUtil.get(redisKey);
        //删除验证码
        redisUtil.delete(redisKey);
        return code.equalsIgnoreCase(captchaCode);
    }

    /**
     * 生成redis key
     */
    private String genRedisKey(String uuid) {
        return RedisKeyConstants.MANAGE_SYS_CAPTCHA + uuid;
    }
}