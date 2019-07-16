package com.songyuankun.wechat.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
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
    private List<Integer> currentTime;
}
