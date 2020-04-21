package com.songyuankun.wechat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
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
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Room extends BaseEntity implements Serializable {
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
