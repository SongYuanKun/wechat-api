package com.songyuankun.wechat.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.Date;

/**
 * 房间预订列表
 *
 * @author songyuankun
 */
@Proxy(lazy = false)
@Entity
@Table(name = "room_appointment")
@ToString
@Getter
@Setter
public class RoomAppointment {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "room_id")
    private Integer roomId;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "status")
    private Integer status;
}
