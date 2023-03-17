package com.songyuankun.wechat.blog.application.handler;

import com.songyuankun.wechat.blog.application.dto.ArticleDTO;
import com.songyuankun.wechat.blog.application.query.FindArticleDetailQuery;
import com.songyuankun.wechat.blog.application.query.FindArticleListQuery;
import com.songyuankun.wechat.blog.domain.assembler.ArticleAssembler;
import com.songyuankun.wechat.blog.domain.repository.ArticleRepository;
import com.songyuankun.wechat.infrastructure.dataaccess.ArticlePO;

import org.springframework.stereotype.Component;

@Component
public class FindArticleDetailQueryHandler implements QueryHandler<FindArticleDetailQuery, ArticleDTO> {

    private final ArticleRepository articleRepository;
    private final ArticleAssembler articleAssembler;

    public FindArticleDetailQueryHandler(ArticleRepository articleRepository, ArticleAssembler articleAssembler) {
        this.articleRepository = articleRepository;
        this.articleAssembler = articleAssembler;
    }

    @Override
    public ArticleDTO handle(FindArticleDetailQuery query) {
        ArticlePO articlePO = articleRepository.findById(query.getId())
            .orElseThrow(() -> new RuntimeException("error"));
        return articleAssembler.toArticleDTO(articlePO);
    }
}