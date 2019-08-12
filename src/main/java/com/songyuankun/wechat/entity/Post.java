package com.songyuankun.wechat.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 内容表
 *
 * @author langhsu
 */
@Entity
@Table(name = "mto_post")
@ToString
@Getter
@Setter
public class Post extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    /**
     * 分组/模块ID
     */
    @Column(name = "channel_id", length = 5)
    private int channelId;

    /**
     * 标题
     */
    @Column(name = "title", length = 64)
    private String title;

    /**
     * 摘要
     */
    @Column(length = 140)
    private String summary;

    /**
     * 预览图
     */
    @Column(length = 128)
    private String thumbnail;

    /**
     * 标签, 多个逗号隔开
     */
    @Column(length = 64)
    private String tags;

    /**
     * 作者Id
     */
    @Column(name = "author_id")
    private long authorId;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Date created;

    /**
     * 收藏数
     */
    private int favors;

    /**
     * 评论数
     */
    private int comments;

    /**
     * 阅读数
     */
    private int views;

    /**
     * 文章状态
     */
    private int status;

    /**
     * 推荐状态
     */
    private int featured;

    /**
     * 排序值
     */
    private int weight;


}