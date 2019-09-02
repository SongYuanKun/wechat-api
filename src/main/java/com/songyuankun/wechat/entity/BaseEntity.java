package com.songyuankun.wechat.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author songyuankun
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {

    @Column(name = "create_user_id")
    private Integer createUserId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @Column(name = "update_user_id")
    private Integer updateUserId;

    @Column(name = "update_time")
    private Date updateTime;

}
