package com.songyuankun.wechat.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
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
public class AppointmentTimePoint extends BaseDao implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "room_id")
    private Integer roomId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "time_point_id")
    private Integer timePointId;

    @Column(name="user_name")
    private String userName;

    private String phone;


    @Transient
    @JoinColumn(name = "time_point_id", updatable = false, insertable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private TimePoint timePoint;

    private Integer status;

    private String statusZh;

    private String day;

}
