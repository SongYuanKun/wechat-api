package com.songyuankun.wechat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

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
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class User extends BaseEntity implements Serializable {
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

    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer age;

    private Integer sex;

    private String birthday;

    private String phone;

}
