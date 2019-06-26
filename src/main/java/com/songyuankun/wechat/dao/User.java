package com.songyuankun.wechat.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author songyuankun
 */
@Proxy(lazy = false)
@Entity
@Table(name = "user")
@ToString
@Getter
@Setter
public class User implements Serializable {
    @Id
    private String uid;
    private Date createTime;
    private String sessionKey;
    private int balance;
    private String key;
    private String address;
    private String avatar;
    private int gender;
    private String name;
    private Date updateTime;


}
