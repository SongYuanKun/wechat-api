package com.songyuankun.wechat.response;

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

    private String startTime;

    private String endTime;

    private String userName;

    private String phone;

    private Integer status;

    private String timeValue;


}
