package com.songyuankun.wechat.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author songyuankun
 */
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
