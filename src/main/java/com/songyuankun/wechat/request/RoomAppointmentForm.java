package com.songyuankun.wechat.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author songyuankun
 */
@ApiModel
@Getter
@Setter
@ToString
public class RoomAppointmentForm implements Serializable {
    private String userName;
    private String phone;
    private Integer roomId;
    private Date startTime;
    private Date endTime;
}
