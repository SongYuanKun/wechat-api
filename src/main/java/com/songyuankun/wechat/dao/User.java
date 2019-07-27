package com.songyuankun.wechat.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 *
 * @author songyuankun
 */
@Proxy(lazy = false)
@Entity
@Table(name = "user")
@ToString
@Getter
@Setter
public class User extends BaseDao implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private String uid;
    @Column(name = "password")
    private String password;
    @Column(name = "session_key")
    private String sessionKey;
    private Integer balance;
    private String address;
    private String avatar;
    private Integer gender;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_role")
    private String userRole;

    private Integer age;

    private Integer sex;

    private String birthday;

    private String phone;

}
