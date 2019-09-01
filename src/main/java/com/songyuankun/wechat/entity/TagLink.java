package com.songyuankun.wechat.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
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
public class TagLink extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键")
    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "article_id")
    private Integer articleId;

    @Column(name = "tag_id")
    private Integer tagId;
}
