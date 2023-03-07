package com.songyuankun.wechat.blog.domain.service;

import com.songyuankun.wechat.blog.application.assembler.ArticleMapper;
import com.songyuankun.wechat.blog.domain.aggregate.Article;
import com.songyuankun.wechat.blog.domain.repository.ArticleRepository;
import com.songyuankun.wechat.infrastructure.dataaccess.ArticlePO;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    @Override
    public Article createArticle(Article article) {
        ArticlePO articlePO = articleMapper.toArticlePO(article);
        articleRepository.save(articlePO);
        return article;
    }

    @Override
    public Article getById(Integer id) {
        ArticlePO articlePO = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article not exit"));
        return articleMapper.toArticle(articlePO);
    }
}
