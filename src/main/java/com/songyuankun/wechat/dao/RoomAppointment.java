package com.songyuankun.wechat.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
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
    @Transient
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private User user;
    @Column(name = "room_id")
    private Integer roomId;
    @Transient
    @JoinColumn(name = "room_id", updatable = false, insertable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Room room;
    private String day;
    @Column(name = "start_time")
    private Date startTime;
    @Column(name = "end_time")
    private Date endTime;
    @Column(name = "status")
    private Integer status;
    @Column(name = "user_name")
    private String userName;
    private String phone;

}
