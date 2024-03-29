package com.songyuankun.wechat.blog.application.handler;

import com.songyuankun.wechat.blog.application.dto.ArticleDTO;
import com.songyuankun.wechat.blog.application.query.FindArticleListQuery;
import com.songyuankun.wechat.blog.domain.assembler.ArticleAssembler;
import com.songyuankun.wechat.blog.domain.repository.ArticleRepository;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FindArticleListQueryHandler implements QueryHandler<FindArticleListQuery, List<ArticleDTO>> {

    private final ArticleRepository articleRepository;
    private final ArticleAssembler articleAssembler;

    public FindArticleListQueryHandler(ArticleRepository articleRepository, ArticleAssembler articleAssembler) {
        this.articleRepository = articleRepository;
        this.articleAssembler = articleAssembler;
    }

    @Override
    public List<ArticleDTO> handle(FindArticleListQuery query) {
        return new ArrayList<>();
    }
}