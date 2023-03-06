package com.songyuankun.wechat.blog.application;

import com.songyuankun.wechat.blog.dao.ArticleRepository;
import com.songyuankun.wechat.blog.domain.aggregate.Article;
import com.songyuankun.wechat.blog.domain.service.ArticleService;
import com.songyuankun.wechat.common.DaoCommon;
import com.songyuankun.wechat.entity.ArticlePO;
import com.songyuankun.wechat.request.ArticlePOForm;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ArticleApplicationService {
    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

    public ArticleApplicationService(ArticleService articleService, ArticleRepository articleRepository) {
        this.articleService = articleService;
        this.articleRepository = articleRepository;
    }

    public Integer createArticle(String title, String content) {
        Article article = articleService.createArticle(title, content);

        return article.getId();
    }

    public Object getArticle(Integer id) {
        ArticlePO article = articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Article not found"));
        return new Object();
    }
}