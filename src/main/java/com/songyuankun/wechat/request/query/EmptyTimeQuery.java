package com.songyuankun.wechat.request.query;

import com.songyuankun.wechat.dao.TimePoint;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel
@Getter
@Setter
@ToString
public class EmptyTimeQuery {
    private TimePoint startTime;
    private TimePoint endTime;
    private String date;
}
