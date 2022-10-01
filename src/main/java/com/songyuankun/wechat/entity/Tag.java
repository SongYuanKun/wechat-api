package com.songyuankun.wechat.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <p>
 * 标签
 * </p>
 *
 * @author bobbi
 * @since 2018-11-07
 */
@Proxy(lazy = false)
@Entity
@Table(name = "tag")
@ToString
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Tag extends BaseEntity implements Serializable {



    @Id
    @GeneratedValue
    private Integer id;


    private String name;


    @Column(nullable = false)
    @ColumnDefault("0")
    private Integer type;


}
