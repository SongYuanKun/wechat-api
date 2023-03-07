package com.songyuankun.wechat.infrastructure.dataaccess;


import com.songyuankun.wechat.entity.BaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 博客
 *
 * @author songyuankun
 */

@Proxy(lazy = false)
@Entity
@Table(name = "article")
@ToString
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticlePO extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "media_id")
    private String mediaId;

    private String title;

    @ColumnDefault("''")
    private String description;

    @ColumnDefault("''")
    private String author;

    @Column(columnDefinition = "text")
    private String content;

    @Column(name = "read_num", nullable = false)
    @ColumnDefault("0")
    private Long readNum;

    @Column(name = "like_num", nullable = false)
    @ColumnDefault("0")
    private Long likeNum;

    private String cover;

    @Column(name = "thumb_media_id")
    @ColumnDefault("''")
    private String thumbMediaId;

    @Column(name = "cover_type")
    @ColumnDefault("0")
    private Integer coverType;

    private Boolean recommend;

    @Column(name = "category_id")
    private String categoryId;

    private Boolean publish;

    private Boolean top;

    @Column(name = "content_format", columnDefinition = "text")
    private String contentFormat;
}