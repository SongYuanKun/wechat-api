package com.songyuankun.wechat.util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * RedisUtils
 *
 * @author songyuankun
 */
@Component
public class RedisUtils {
    /**
     * 默认过期时长，单位：秒
     */
    private static final long DEFAULT_EXPIRE = 60 * 60 * 24;
    /**
     * 不设置过期时长
     */
    private static final long NOT_EXPIRE = -1;
    private final RedisTemplate redisTemplate;
    private final ValueOperations<String, String> valueOperations;

    public RedisUtils(RedisTemplate redisTemplate, ValueOperations<String, String> valueOperations) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = valueOperations;

    }

    /**
     * 设置值与过期时间
     *
     * @param key
     * @param value
     * @param expire
     */
    public void set(String key, Object value, long expire) {
        valueOperations.set(key, JSONObject.toJSONString(value));
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void setString(String key, String value, long expire) {
        valueOperations.set(key, value);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }


    /**
     * 设置值，默认过期时间1天
     *
     * @param key
     * @param value
     */
    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    /**
     * 获取对象，同时设置过期时间
     *
     * @param key
     * @param clazz
     * @param expire
     * @param <T>
     * @return
     */
    public <T> T getObj(String key, Class<T> clazz, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : JSONObject.parseObject(value, clazz);
    }

    /**
     * 获取对象，不设置过期时间
     *
     * @param key
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getObj(String key, Class<T> clazz) {
        return getObj(key, clazz, NOT_EXPIRE);
    }

    /**
     * 获取值，同时设置过期时间
     *
     * @param key
     * @param expire
     * @return
     */
    public String get(String key, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    /**
     * 获取值，不设置过期时间
     *
     * @param key
     * @return
     */
    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    /**
     * 删除
     *
     * @param key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 更新过期时间
     *
     * @param key
     */
    public void updateExpire(String key) {
        redisTemplate.expire(key, DEFAULT_EXPIRE, TimeUnit.SECONDS);
    }


}
