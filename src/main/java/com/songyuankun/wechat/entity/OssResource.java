package com.songyuankun.wechat.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 云存储资源表
 *
 * @author songyuankun
 */
@Proxy(lazy = false)
@Entity
@Table(name = "course")
@ToString
@Getter
@Setter
@ApiModel(value = "OssResource对象", description = "云存储资源表")
public class OssResource extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    private String url;

    private String key;

    public OssResource(String url, String name, String key) {
        this.name = name;
        this.url = url;
        this.key = key;
    }

}
