package com.songyuankun.wechat.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author songyuankun
 */
@ApiModel
@Getter
@Setter
@ToString
public class RoomAppointmentForm implements Serializable {
    @NotEmpty
    private String userName;
    @NotEmpty
    private String phone;
    @NotEmpty
    private String day;
    @NotNull
    private Integer startTime;
    @NotNull
    private Integer endTime;


    @NotEmpty
    private List<Integer> currentTime;
}
