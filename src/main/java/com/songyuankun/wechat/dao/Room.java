package com.songyuankun.wechat.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 房间表
 *
 * @author songyuankun
 */
@Proxy(lazy = false)
@Entity
@Table(name = "room")
@ToString
@Getter
@Setter
public class Room extends BaseDao implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer length;
    private Integer width;
    @Column(name = "status")
    private Integer status;
    private String img;

}
