package com.songyuankun.wechat.response;

import com.songyuankun.wechat.dao.TimePoint;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author songyuankun
 */
@ApiModel
@Getter
@Setter
@ToString
public class MyAppointmentTimeResponse {

    private Integer id;

    private String day;

    private Integer timePointId;

    private TimePoint timePoint;

    private String userName;

    private String phone;

    private Integer status;


}
