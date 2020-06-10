package com.songyuankun.wechat.enums;

import lombok.Getter;

/**
 * @author songyuankun
 */

@Getter
public enum EnvEnum {

    /**
     *
     */
    BAI("bai"),


    HUSKY("husky");

    private final String value;

    EnvEnum(String value) {
        this.value = value;
    }

}
