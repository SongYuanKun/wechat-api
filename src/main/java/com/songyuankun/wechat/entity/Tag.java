package com.songyuankun.wechat.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * <p>
 * 标签
 * </p>
 *
 * @author bobbi
 * @since 2018-11-07
 */
@Proxy(lazy = false)
@Entity
@Table(name = "tag")
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Tag extends BaseEntity implements Serializable {


    @ApiModelProperty(value = "主键")
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "标签名字")
    private String name;

    @ApiModelProperty(value = "所属类型：0文章，1阅读")
    private Integer type;


}
