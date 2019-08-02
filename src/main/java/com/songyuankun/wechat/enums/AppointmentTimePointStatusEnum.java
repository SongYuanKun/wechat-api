package com.songyuankun.wechat.enums;

import lombok.Getter;

@Getter
public enum AppointmentTimePointStatusEnum {
    /*
     *
     */
    SUCCESS(0, "预约成功"),

    SIGN_IN(1, "已到店"),
    CANCEL(2, "已取消");


    private int value = 0;
    private String description = "";

    AppointmentTimePointStatusEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }
}
