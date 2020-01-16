package com.songyuankun.wechat.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.*;

/**
 * 预约时间段表
 *
 * @author songyuankun
 */

@ToString
@Getter
@Setter
public class TimePoint {

    private static final Map<Integer, TimePoint> TIME_POINT_MAP = new HashMap<>(32);
    private static final List<TimePoint> TIME_POINT_LIST = new ArrayList<>();

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(new Date());
        endCalendar.set(Calendar.HOUR_OF_DAY, 22);
        endCalendar.set(Calendar.MINUTE, 0);
        endCalendar.set(Calendar.SECOND, 0);
        Date finishTime = endCalendar.getTime();

        int i = 1;
        while (true) {
            Date startTime = calendar.getTime();
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            Date endTime = calendar.getTime();
            if (finishTime.before(endTime)) {
                break;
            }
            TimePoint timePoint = createTimePoint(i, startTime, endTime);
            TIME_POINT_LIST.add(timePoint);
            TIME_POINT_MAP.put(i, timePoint);
            i++;
        }
    }

    private int id;
    private String startTime;
    private String endTime;
    private int status;
    private int type;
    /**
     * 页面显示值
     */
    private String value;

    private TimePoint(int id, String startTime, String endTime, int status, String value, int type) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.value = value;
        this.type = type;
    }

    public static Map<Integer, TimePoint> getTimePointMap() {
        return TIME_POINT_MAP;
    }

    public static List<TimePoint> getTimePointList() {
        return TIME_POINT_LIST;
    }

    private static TimePoint createTimePoint(int i, Date startTime, Date endTime) {
        String start = DateFormatUtils.format(startTime, "HH:mm");
        String end = DateFormatUtils.format(endTime, "HH:mm");
        return new TimePoint(i, start, end, 0, start + "-" + end, 1);
    }
}
