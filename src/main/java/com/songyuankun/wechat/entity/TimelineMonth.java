package com.songyuankun.wechat.entity;

import lombok.*;
import org.hibernate.annotations.Proxy;

import java.util.List;

@Proxy(lazy = false)
@Data
@ToString
@Getter
@Setter
@NoArgsConstructor
public class TimelineMonth {

    private Integer month;

    private Integer count;

    private List<TimelinePost> posts;

}
