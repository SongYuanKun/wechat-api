package com.songyuankun.wechat.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Proxy;

import java.util.List;

@ApiModel
@Proxy(lazy = false)
@Data
@ToString
@Getter
@Setter
public class Timeline {

    @ApiModelProperty("年份")
    private Integer year;

    @ApiModelProperty("数量")
    private Integer count;

    @ApiModelProperty("每月数据")
    private List<TimelineMonth> months;

    public Timeline(Integer year, Integer count) {
        this.year = year;
        this.count = count;
    }
}
