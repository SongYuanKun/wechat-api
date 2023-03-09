package com.songyuankun.wechat.blog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class ArticleDTO {

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

}
