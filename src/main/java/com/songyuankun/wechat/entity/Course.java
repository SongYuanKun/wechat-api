package com.songyuankun.wechat.entity;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 课程表
 *
 * @author songyuankun
 */
@Proxy(lazy = false)
@Entity
@Table(name = "course")
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Builder
public class Course extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    /**
     * 创建用户
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 课程名称
     */
    @Column(name = "name")
    private String name;


    /**
     * 课程状态
     */
    @Column(name = "status")
    private Integer status;
    /**
     * 课程封面
     */
    private String img;

    /**
     * 教师名称
     */
    @Column(name = "teacher_name")
    private String teacherName;
    /**
     * 课程简介
     */
    private String introduction;

    /**
     * 课程详细介绍
     */
    @Column(name = "content_html")
    private String contentHtml;


}
