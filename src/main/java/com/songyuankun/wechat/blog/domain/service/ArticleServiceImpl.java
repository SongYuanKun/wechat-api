package com.songyuankun.wechat.blog.domain.service;

import com.songyuankun.wechat.blog.dao.ArticleRepository;
import com.songyuankun.wechat.blog.domain.aggregate.Article;
import com.songyuankun.wechat.entity.ArticlePO;

import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article createArticle(Article article) {
        articleRepository.save(new ArticlePO());
        return article;
    }

    @Override
    public Article getById(Integer id) {
        articleRepository.findById(id);
        return new Article();
    }
}
