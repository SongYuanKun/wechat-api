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
 * @author songyuankun
 */
@Proxy(lazy = false)
@Entity
@Table(name = "category")
@ToString
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Category extends BaseEntity implements Serializable {


    @Id
    @GeneratedValue
    private Integer id;


    @Column(name = "name")
    private String name;


    @ColumnDefault(value = "0")
    private Integer type;


    @ColumnDefault(value = "0")
    @Column(name = "c_rank")
    private Integer rank;


    @Column(name = "parent_id")
    @ColumnDefault(value = "0")
    private Integer parentId;

}
