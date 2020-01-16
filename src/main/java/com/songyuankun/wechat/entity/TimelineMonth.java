package com.songyuankun.wechat.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.annotations.Proxy;

import java.util.List;

@ApiModel
@Proxy(lazy = false)
@Data
@ToString
@Getter
@Setter
@NoArgsConstructor
public class TimelineMonth {

    @ApiModelProperty("月份")
    private Integer month;

    @ApiModelProperty("数量")
    private Integer count;

    @ApiModelProperty("文章列表")
    private List<TimelinePost> posts;

}
