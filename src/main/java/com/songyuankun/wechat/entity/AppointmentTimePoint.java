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
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class AppointmentTimePoint extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "room_id")
    private Integer roomId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "time_point_ids")
    private String timePointIds;

    @Column(name = "user_name")
    private String userName;

    private String phone;

    private Integer status;

    private String day;

    private String startTime;

    private String endTime;

}
