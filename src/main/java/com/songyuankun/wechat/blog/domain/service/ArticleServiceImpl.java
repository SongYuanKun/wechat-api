package com.songyuankun.wechat.blog.domain.service;

import com.songyuankun.wechat.blog.dao.ArticleRepository;
import com.songyuankun.wechat.blog.domain.aggregate.Article;

import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article createArticle(String title, String content) {

        return null;
    }
}
