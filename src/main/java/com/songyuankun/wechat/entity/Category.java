package com.songyuankun.wechat.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "类型")
    @NotNull(message = "类型不能为空")
    private Integer type;

    @ApiModelProperty(value = "级别")
    @NotNull(message = "级别不能为空")
    private Integer rank;

    @ApiModelProperty(value = "父主键")
    @NotNull(message = "父主键不能为空")
    private Integer parentId;

    @Transient
    private String parentName;


}
