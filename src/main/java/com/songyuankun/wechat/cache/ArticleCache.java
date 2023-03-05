package com.songyuankun.wechat.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArticleCache {
    private static final String READ_COUNT_PREFIX = "article:read:";
    private static final String LIKE_COUNT_PREFIX = "article:like:";
    private RedisTemplate<String, Object> redisTemplate;

    public ArticleCache(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void incrementReadCount(Long articleId) {
        String key = READ_COUNT_PREFIX + articleId;
        redisTemplate.opsForValue().increment(key);
    }

    public Long getReadCount(Long articleId) {
        String key = READ_COUNT_PREFIX + articleId;
        Object value = redisTemplate.opsForValue().get(key);
        return value == null ? 0 : (Long) value;
    }

    public void incrementLikeCount(Long articleId) {
        String key = LIKE_COUNT_PREFIX + articleId;
        redisTemplate.opsForValue().increment(key);
    }

    public Long getLikeCount(Long articleId) {
        String key = LIKE_COUNT_PREFIX + articleId;
        Object value = redisTemplate.opsForValue().get(key);
        return value == null ? 0 : (Long) value;
    }
}
