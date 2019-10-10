package com.songyuankun.wechat.entity;

import lombok.*;
import org.hibernate.annotations.Proxy;

import javax.persistence.Column;
import java.util.Date;

@Proxy(lazy = false)
@Data
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimelinePost {

    private Integer id;

    private String title;

    private String description;

    @Column(name = "create_time")
    private Date createTime;

}
