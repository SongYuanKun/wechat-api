package com.songyuankun.wechat.blog.domain.service;

import com.songyuankun.wechat.blog.domain.aggregate.Article;

import org.springframework.stereotype.Service;

public interface ArticleService {
    Article createArticle(String title, String content);
}