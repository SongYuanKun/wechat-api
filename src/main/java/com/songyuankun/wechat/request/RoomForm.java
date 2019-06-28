package com.songyuankun.wechat.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author songyuankun
 */
@ApiModel
@Getter
@Setter
@ToString
public class RoomForm implements Serializable {
    private String name;
    private Integer length;
    private Integer width;
    private String img;
}
