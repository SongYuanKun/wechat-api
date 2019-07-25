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
public class UserForm implements Serializable {
    private Integer id;
    private String userName;
    private String phone;
    private String address;
    private Integer sex;

}
