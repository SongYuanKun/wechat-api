package com.songyuankun.wechat.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Proxy;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 预约时间段表
 *
 * @author songyuankun
 */
@Proxy(lazy = false)
@Entity
@Table(name = "time_point")
@ToString
@Getter
@Setter
public class TimePoint implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 类型
     */
    private Integer type;

    private String value;


}
