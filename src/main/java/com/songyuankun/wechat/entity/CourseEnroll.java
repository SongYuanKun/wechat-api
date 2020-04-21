package com.songyuankun.wechat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;

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
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class CourseEnroll extends BaseEntity implements Serializable {

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
