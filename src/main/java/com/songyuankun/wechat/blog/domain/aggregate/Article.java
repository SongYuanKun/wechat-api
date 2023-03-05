package com.songyuankun.wechat.blog.domain.aggregate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 博客信息的领域模型
 *
 * @author songyuankun
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    private Integer id;
    private String mediaId;
    private String title;
    private String description;
    private String author;
    private String content;
    private Long readNum;
    private Long likeNum;
    private String cover;
    private String thumbMediaId;
    private Integer coverType;
    private Boolean recommend;
    private String categoryId;
    private Boolean publish;
    private Boolean top;

    public Article(String mediaId, String title, String description, String author, String content, String cover, String thumbMediaId, Integer coverType, String categoryId) {
        this.mediaId = mediaId;
        this.title = title;
        this.description = description;
        this.author = author;
        this.content = content;
        this.cover = cover;
        this.thumbMediaId = thumbMediaId;
        this.coverType = coverType;
        this.categoryId = categoryId;
    }

    public static Article createArticle(String mediaId, String title, String description, String author, String content, String cover, String thumbMediaId, Integer coverType, String categoryId) {
        return new Article(mediaId, title, description, author, content, cover, thumbMediaId, coverType, categoryId);
    }

    public Article updateArticle(Article updateArticle) {
        this.setMediaId(updateArticle.getMediaId());
        this.setTitle(updateArticle.getTitle());
        this.setDescription(updateArticle.getDescription());
        this.setAuthor(updateArticle.getAuthor());
        this.setContent(updateArticle.getContent());
        this.setCover(updateArticle.getCover());
        this.setThumbMediaId(updateArticle.getThumbMediaId());
        this.setCoverType(updateArticle.getCoverType());
        this.setRecommend(updateArticle.getRecommend());
        this.setCategoryId(updateArticle.getCategoryId());
        return this;
    }

    public void toPublish() {
        this.publish = true;
    }

    public void toTop() {
        this.top = true;
    }

    public void cancelTop() {
        this.top = true;
    }

}
