package com.songyuankun.wechat.dao;

import lombok.Getter;
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
@MappedSuperclass
public abstract class BaseDao {

    @Column(name = "create_user_id")
    private Integer createUserId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_user_id")
    private Integer updateUserId;

    @Column(name = "update_time")
    private Date updateTime;

}
