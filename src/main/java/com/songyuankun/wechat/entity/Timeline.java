package com.songyuankun.wechat.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import java.util.List;

@Proxy(lazy = false)
@Data
@ToString
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
public class Timeline {

    private Integer year;

    private Integer count;

    private List<TimelineMonth> months;

    public Timeline(Integer year, Integer count) {
        this.year = year;
        this.count = count;
    }
}
