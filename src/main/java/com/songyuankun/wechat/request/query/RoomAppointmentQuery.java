package com.songyuankun.wechat.request.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author songyuankun
 */
@Getter
@Setter
@ToString
public class RoomAppointmentQuery extends BasePageQuery {
    private String userName;
    private String day;
}
