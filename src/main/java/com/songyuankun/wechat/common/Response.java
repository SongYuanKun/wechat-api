package com.songyuankun.wechat.common;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author songyuankun
 */
@Getter
@Setter
@ToString
public class Response<T> {
    private Integer code;
    private T data;
}
