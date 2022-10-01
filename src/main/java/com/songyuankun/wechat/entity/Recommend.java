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
@Table(name = "recommend")
@ToString
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Recommend extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue
    private Integer id;


    @Column(name = "link_id")
    private Integer linkId;


    private Integer type;


    @Column(name = "order_num")
    private Integer orderNum;


    private String title;


    private Boolean top;


}
