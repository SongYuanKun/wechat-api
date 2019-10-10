package com.songyuankun.wechat.entity;

import lombok.*;
import org.hibernate.annotations.Proxy;

import java.util.List;

@Proxy(lazy = false)
@Data
@ToString
@Getter
@Setter
public class Timeline {

    private Integer year;

    private Integer count;

    private List<TimelineMonth> months;

    public Timeline(Integer year, Integer count) {
        this.year = year;
        this.count = count;
    }
}
