package com.songyuankun.wechat.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
public class Article extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键")
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章描述")
    private String description;

    @ApiModelProperty(value = "文章作者")
    private String author;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "阅读量")
    @Column(name = "read_num")
    private Long readNum;

    @ApiModelProperty(value = "点赞量")
    @Column(name = "like_num")
    private Long likeNum;

    @ApiModelProperty(value = "封面")
    private String cover;

    @ApiModelProperty(value = "文章展示类别,0:普通，1：大图片，2：无图片")
    @Column(name = "cover_type")
    private Integer coverType;

    @ApiModelProperty(value = "是否推荐文章")
    private Boolean recommend;

    @ApiModelProperty(value = "分类类别")
    @Column(name = "category_id")
    private String categoryId;

    @ApiModelProperty(value = "发布状态")
    private Boolean publish;

    @ApiModelProperty(value = "是否置顶")
    private Boolean top;

    @ApiModelProperty(value = "格式化后的内容")
    @Column(name = "content_format")
    private String contentFormat;
}