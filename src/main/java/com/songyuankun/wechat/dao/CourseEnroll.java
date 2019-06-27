package com.songyuankun.wechat.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 课程报名表
 *
 * @author songyuankun
 */
@Proxy(lazy = false)
@Entity
@Table(name = "course_enroll")
@ToString
@Getter
@Setter
public class CourseEnroll implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "course_id")
    private Integer courseId;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "status")
    private Integer status;
}
