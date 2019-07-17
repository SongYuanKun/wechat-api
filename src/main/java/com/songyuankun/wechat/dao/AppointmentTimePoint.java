package com.songyuankun.wechat.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 房间被预订的时间点
 *
 * @author songyuankun
 */
@Proxy(lazy = false)
@Entity
@Table(name = "appointment_time_point")
@ToString
@Getter
@Setter
public class AppointmentTimePoint implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "room_id")
    private Integer roomId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "time_point_id")
    private Integer timePointId;

    private Integer status;

    private String statusZh;

    private String day;

}
