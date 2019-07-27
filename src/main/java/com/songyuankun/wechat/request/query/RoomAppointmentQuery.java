package com.songyuankun.wechat.request.query;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @author songyuankun
 */
@ApiModel
@Getter
@Setter
@ToString
public class RoomAppointmentQuery {
    private String userName;
    private String day;
    @NotNull
    private Integer pageNumber;
    @NotNull
    private Integer pageSize;
}
