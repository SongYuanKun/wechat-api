package com.songyuankun.wechat.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 分类表
 *
 * @author songyuankun
 */
@Proxy(lazy = false)
@Entity
@Table(name = "classification")
@ToString
@Getter
@Setter
public class Classification extends BaseEntity {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    private Integer status;
}
