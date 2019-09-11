package com.songyuankun.wechat.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 房间被预订的时间点
 *
 * @author songyuankun
 */
@Proxy(lazy = false)
@Entity
@Table(name = "recommend")
@ToString
@Getter
@Setter
@NoArgsConstructor
public class Recommend extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @Id
    @GeneratedValue
    private Integer id;

    @ApiModelProperty(value = "推荐的文章Id")
    @Column(name = "link_id")
    private Integer linkId;

    @ApiModelProperty(value = "推荐类型")
    private Integer type;

    @ApiModelProperty(value = "顺序")
    @Column(name = "order_num")
    private Integer orderNum;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "置顶")
    private Boolean top;


}
