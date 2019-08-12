package com.songyuankun.wechat.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

/**
 * 文稿表
 *
 * @author songyuankun
 */
@Proxy(lazy = false)
@Entity
@Table(name = "catalogue")
@ToString
@Getter
@Setter
public class Catalogue extends BaseEntity {
    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private String contentHtml;

    private String tag;

    private String author;

    /**
     * 创建用户
     */
    @Column(name = "user_id")
    private Integer userId;


    @Column(name = "update_id")
    private Integer updateId;

    /**
     * 课程状态
     */
    @Column(name = "status")
    private Integer status;

    @Column(name = "classification_id")
    private Integer classificationId;

    @Transient
    @JoinColumn(name = "classification_id", updatable = false, insertable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Classification classification;
}
