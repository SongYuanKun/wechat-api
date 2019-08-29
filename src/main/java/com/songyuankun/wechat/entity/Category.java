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
@Table(name = "category")
@ToString
@Getter
@Setter
public class Category extends BaseEntity implements Serializable {

    @ApiModelProperty(value = "主键")
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "名称")
    @Column(name = "name")
    private String name;

    @ApiModelProperty(value = "类型")
    @ColumnDefault(value = "0")
    private Integer type;

    @ApiModelProperty(value = "级别")
    @ColumnDefault(value = "0")
    @Column(name = "c_rank")
    private Integer rank;

    @ApiModelProperty(value = "父主键")
    @Column(name = "parent_id")
    @ColumnDefault(value = "0")
    private Integer parentId;

    @Transient
    private String parentName;


}
