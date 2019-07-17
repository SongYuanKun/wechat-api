package com.songyuankun.wechat.enums;

import lombok.Getter;

/**
 * @author 付款状态
 */

@Getter
public enum PayStatusEnum {
    /*
     *
     */
    WAIT_PAY(0, "待付款"),

    SUCCESS_PAY(1, "已付款"),
    CANCEL_PAY(2, "取消付款");


    private int value = 0;
    private String description = "";

    PayStatusEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }
}
