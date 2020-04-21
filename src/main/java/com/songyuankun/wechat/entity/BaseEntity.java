package com.songyuankun.wechat.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

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
@DynamicInsert
@DynamicUpdate
public class BaseEntity {

    @ApiModelProperty(value = "创建人")
    @Column(name = "create_user_id")
    private Integer createUserId;

    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新人")
    @Column(name = "update_user_id")
    private Integer updateUserId;

    @ApiModelProperty(value = "更新时间")
    @Column(name = "update_time")
    private Date updateTime;

}
