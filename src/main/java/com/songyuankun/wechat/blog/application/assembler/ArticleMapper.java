package com.songyuankun.wechat.blog.application.assembler;

import com.songyuankun.wechat.blog.domain.aggregate.Article;
import com.songyuankun.wechat.infrastructure.dataaccess.ArticlePO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    ArticlePO toArticlePO(Article article);

    Article toArticle(ArticlePO articlePO);

}
