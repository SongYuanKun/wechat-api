package com.songyuankun.wechat.dao;

import lombok.Getter;
import lombok.ToString;

/**
 * 预约时间段表
 *
 * @author songyuankun
 */

@ToString
@Getter
public enum TimePoint {
    /**
     *
     */
    TIME1(1, "9:00-9:30", 0),
    TIME2(2, "9:30-10:00", 0),
    TIME3(3, "10:00-10:30", 0),
    TIME4(4, "10:30-11:00", 0),
    TIME5(5, "11:00-11:30", 0),
    TIME6(6, "11:30-12:00", 0),
    TIME7(7, "12:00-12:30", 0),
    TIME8(8, "12:30-13:00", 0),
    TIME9(9, "13:00-13:30", 0),
    TIME10(10, "13:30-14:00", 0),
    TIME11(11, "14:00-14:30", 0),
    TIME12(12, "14:30-15:00", 0),
    TIME13(13, "15:00-15:30", 0),
    TIME14(14, "15:30-16:00", 0),
    TIME15(15, "16:00-16:30", 0),
    TIME16(16, "16:30-17:00", 0),
    TIME17(17, "17:00-17:30", 0),
    TIME18(18, "17:30-18:00", 0),
    TIME19(19, "18:00-18:30", 0),
    TIME20(20, "18:30-19:00", 0),
    TIME21(21, "19:00-19:30", 0),
    TIME22(22, "19:30-20:00", 0),
    TIME23(23, "20:00-20:30", 0),
    TIME24(24, "20:30-21:00", 0),
    TIME25(25, "21:00-21:30", 0),
    TIME26(26, "21:30-22:00", 0);

    private Integer id;

    /**
     * 页面显示值
     */
    private String value = "";


    /**
     * 类型
     */
    private Integer type;

    TimePoint(Integer id, String value, Integer type) {
        this.id = id;
        this.value = value;
        this.type = type;
    }
}
