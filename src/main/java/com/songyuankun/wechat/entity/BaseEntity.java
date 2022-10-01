package com.songyuankun.wechat.entity;


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


    @Column(name = "create_user_id")
    private Integer createUserId;


    @Column(name = "create_time")
    private Date createTime;


    @Column(name = "update_user_id")
    private Integer updateUserId;


    @Column(name = "update_time")
    private Date updateTime;
}
