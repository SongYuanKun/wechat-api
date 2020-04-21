package com.songyuankun.wechat.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author songyuankun
 */
@ApiModel
@Proxy(lazy = false)
@Data
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
public class TimelinePost {

    @ApiModelProperty("文章id")
    private Integer id;

    @ApiModelProperty("文章标题")
    private String title;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("创建时间")
    @Column(name = "create_time")
    private Date createTime;

}
