package com.songyuankun.wechat.blog.application.assembler;

import com.songyuankun.wechat.blog.domain.aggregate.Article;
import com.songyuankun.wechat.infrastructure.dataaccess.ArticlePO;

import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

    public ArticlePO toArticlePO(Article article) {
        return ArticlePO.builder()
            .id(article.getId())
            .mediaId(article.getMediaId())
            .title(article.getTitle())
            .description(article.getDescription())
            .author(article.getAuthor())
            .content(article.getContent())
            .readNum(article.getReadNum())
            .likeNum(article.getLikeNum())
            .cover(article.getCover())
            .thumbMediaId(article.getThumbMediaId())
            .coverType(article.getCoverType())
            .recommend(article.getRecommend())
            .categoryId(article.getCategoryId())
            .publish(article.getPublish())
            .top(article.getTop())
            .build();

    }

    public Article toArticle(ArticlePO articlePO) {
        return Article.builder()
            .id(articlePO.getId())
            .mediaId(articlePO.getMediaId())
            .title(articlePO.getTitle())
            .description(articlePO.getDescription())
            .author(articlePO.getAuthor())
            .content(articlePO.getContent())
            .readNum(articlePO.getReadNum())
            .likeNum(articlePO.getLikeNum())
            .cover(articlePO.getCover())
            .thumbMediaId(articlePO.getThumbMediaId())
            .coverType(articlePO.getCoverType())
            .recommend(articlePO.getRecommend())
            .categoryId(articlePO.getCategoryId())
            .publish(articlePO.getPublish())
            .top(articlePO.getTop())
            .build();


    }

}
