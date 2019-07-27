package com.songyuankun.wechat.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
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
public class CourseEnroll extends BaseDao implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "course_id")
    private Integer courseId;
    @Transient
    @JoinColumn(name = "course_id", updatable = false, insertable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Course course;
    @Column(name = "status")
    private Integer status;
    private String phone;
}
