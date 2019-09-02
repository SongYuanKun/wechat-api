package com.songyuankun.wechat.request.query;


import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author songyuankun
 */
@ApiModel
@Getter
@Setter
@ToString
public class MyAppointmentQuery extends BasePageQuery {
    private Date startTime;
}
