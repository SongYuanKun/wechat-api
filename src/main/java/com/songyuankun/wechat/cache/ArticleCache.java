package com.songyuankun.wechat.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 阅读、点赞、缓存
 *
 * @author songyuankun
 */
@Component
public class ArticleCache {
    private static final String READ_COUNT_PREFIX = "article:read:";
    private static final String LIKE_COUNT_PREFIX = "article:like:";
    private final RedisTemplate<String, Object> redisTemplate;

    public ArticleCache(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void incrementReadCount(Integer articleId) {
        String key = READ_COUNT_PREFIX + articleId;
        redisTemplate.opsForValue().increment(key);
    }

    public Long getReadCount(Integer articleId) {
        String key = READ_COUNT_PREFIX + articleId;
        Object value = redisTemplate.opsForValue().get(key);
        return value == null ? 0 : (Long) value;
    }

    public void incrementLikeCount(Integer articleId) {
        String key = LIKE_COUNT_PREFIX + articleId;
        redisTemplate.opsForValue().increment(key);
    }

    public Long getLikeCount(Integer articleId) {
        String key = LIKE_COUNT_PREFIX + articleId;
        Object value = redisTemplate.opsForValue().get(key);
        return value == null ? 0 : (Long) value;
    }
}
