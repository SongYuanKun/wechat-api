package com.songyuankun.wechat.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author songyuankun
 */
@ToString
@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

    @Column(name = "create_user_id")
    @ColumnDefault(value = "0")
    private Integer createUserId;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @Column(name = "update_user_id")
    @ColumnDefault(value = "0")
    private Integer updateUserId;

    @Column(name = "update_time")
    private Date updateTime;

}
