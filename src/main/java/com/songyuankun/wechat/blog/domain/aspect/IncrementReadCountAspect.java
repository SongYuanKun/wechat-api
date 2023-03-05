package com.songyuankun.wechat.blog.domain.aspect;

import com.songyuankun.wechat.cache.ArticleCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class IncrementReadCountAspect {
    @Autowired
    private ArticleCache articleCache;

    @After("@annotation(com.songyuankun.wechat.blog.domain.annotations.IncrementReadCount)")
    public void incrementReadCount(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            Long articleId = (Long) args[0];
            articleCache.incrementReadCount(articleId);
        }
    }
}
