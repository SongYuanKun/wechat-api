package com.songyuankun.wechat.blog.domain.assembler;

import com.songyuankun.wechat.blog.application.dto.ArticleDTO;
import com.songyuankun.wechat.blog.domain.aggregate.Article;
import com.songyuankun.wechat.infrastructure.dataaccess.ArticlePO;
import org.springframework.stereotype.Component;

@Component
public class ArticleAssembler {

    public Article toArticle(ArticleDTO articleDTO) {
        return Article.builder()
                .id(articleDTO.getId())
                .mediaId(articleDTO.getMediaId())
                .title(articleDTO.getTitle())
                .description(articleDTO.getDescription())
                .author(articleDTO.getAuthor())
                .content(articleDTO.getContent())
                .readNum(articleDTO.getReadNum())
                .likeNum(articleDTO.getLikeNum())
                .cover(articleDTO.getCover())
                .thumbMediaId(articleDTO.getThumbMediaId())
                .coverType(articleDTO.getCoverType())
                .recommend(articleDTO.getRecommend())
                .categoryId(articleDTO.getCategoryId())
                .publish(articleDTO.getPublish())
                .top(articleDTO.getTop())
                .build();
    }

    public ArticleDTO toArticleDTO(Article article) {
        return ArticleDTO.builder()
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

    public ArticleDTO toArticleDTO(ArticlePO articlePO) {
        return ArticleDTO.builder()
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
