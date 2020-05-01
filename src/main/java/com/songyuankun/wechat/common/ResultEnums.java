package com.songyuankun.wechat.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * @author songyuankun
 */
@Getter
@ToString
@AllArgsConstructor
public enum ResultEnums {

    /**
     *
     */
    SUCCESS(0, "请求成功"),
    ERROR(1, "请求失败"),
    NOT_FOUND(404, "网页丢失"),
    SYSTEM_ERROR(1000, "系统异常");

    private Integer code;
    private String message;


}