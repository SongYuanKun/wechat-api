package com.songyuankun.wechat.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author songyuankun
 */
@Proxy(lazy = false)
@Entity
@Table(name = "tag_link")
@ToString
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class TagLink extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键")
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "article_id", nullable = false)
    private Integer articleId;

    @Column(name = "tag_id", nullable = false)
    private Integer tagId;

    public TagLink(Integer articleId, Integer tagId) {
        this.articleId = articleId;
        this.tagId = tagId;
    }
}
