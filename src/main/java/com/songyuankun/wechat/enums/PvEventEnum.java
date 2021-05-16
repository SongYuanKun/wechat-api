package com.songyuankun.wechat.enums;

import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author 付款状态
 */

@Getter
public enum PvEventEnum {
    /*
     *
     */
    READ(1, "阅读数"),

    LIKE(2, "点赞数"),
    ;


    private int value = 0;
    private String description = "";

    PvEventEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public static PvEventEnum getPvEventEnum(Integer i) {
        for (PvEventEnum p : PvEventEnum.values()) {
            if (!ObjectUtils.notEqual(p.value, i)) {
                return p;
            }
        }
        return null;
    }


}
