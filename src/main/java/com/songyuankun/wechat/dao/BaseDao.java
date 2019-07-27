package com.songyuankun.wechat.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@ToString
@Getter
@Setter
@MappedSuperclass
class BaseDao {

    @Column(name = "create_user_id")
    private int createUserId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_user_id")
    private int updateUserId;

    @Column(name = "update_time")
    private Date updateTime;

}
