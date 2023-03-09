package com.songyuankun.wechat.blog.application.handler;

import com.songyuankun.wechat.blog.application.assembler.ArticleMapper;
import com.songyuankun.wechat.blog.domain.aggregate.Article;
import com.songyuankun.wechat.blog.domain.assembler.ArticleAssembler;
import com.songyuankun.wechat.blog.domain.command.CreateArticleCommand;
import com.songyuankun.wechat.blog.domain.repository.ArticleRepository;
import com.songyuankun.wechat.infrastructure.dataaccess.ArticlePO;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class CreateArticleCommandHandler implements CommandHandler<CreateArticleCommand, Integer> {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;
    private final ArticleAssembler articleAssembler;


    public CreateArticleCommandHandler(ArticleRepository articleRepository, ArticleMapper articleMapper, ArticleAssembler articleAssembler) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
        this.articleAssembler = articleAssembler;
    }

    @Override
    @Transactional
    public Integer handle(CreateArticleCommand command) {
        Article article = articleAssembler.toArticle(command.getArticleDTO());
        ArticlePO articlePO = articleMapper.toArticlePO(article);
        articleRepository.save(articlePO);
        return articlePO.getId();
    }
}
