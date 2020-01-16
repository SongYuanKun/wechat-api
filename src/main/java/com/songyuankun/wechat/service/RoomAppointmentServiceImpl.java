package com.songyuankun.wechat.service;

import com.songyuankun.wechat.common.DaoCommon;
import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.AppointmentTimePoint;
import com.songyuankun.wechat.entity.TimePoint;
import com.songyuankun.wechat.repository.AppointmentTimePointRepository;
import com.songyuankun.wechat.request.RoomAppointmentForm;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author songyuankun
 */
@Service
public class RoomAppointmentServiceImpl {
    private final AppointmentTimePointRepository appointmentTimePointRepository;

    public RoomAppointmentServiceImpl(AppointmentTimePointRepository appointmentTimePointRepository) {
        this.appointmentTimePointRepository = appointmentTimePointRepository;
    }

    public List<TimePoint> queryEmptyTimeTime(String date) {
        Calendar calendar = Calendar.getInstance();
        String day = DateFormatUtils.format(calendar, "yyyy-MM-dd");
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        String format = DateFormatUtils.format(calendar, "HH:mm");
        List<AppointmentTimePoint> appointmentTimePoints = appointmentTimePointRepository.findAllByDay(date);
        List<String> split = new ArrayList<>();
        for (AppointmentTimePoint appointmentTimePoint : appointmentTimePoints) {
            split.addAll(Arrays.asList(appointmentTimePoint.getTimePointIds().split(",")));
        }
        List<Integer> notEmptyTimeList = new ArrayList<>();
        for (String s : split) {
            if (StringUtils.isNotEmpty(s)) {
                notEmptyTimeList.add(Integer.parseInt(s));
            }
        }
        List<TimePoint> all = new ArrayList<>(TimePoint.getTimePointList());
        all.forEach(t -> {
            t.setStatus(notEmptyTimeList.contains(t.getId()) ? 1 : 0);
            if (day.equals(date) && format.compareTo(t.getStartTime()) > 0) {
                t.setStatus(2);
            }
        });
        return all;
    }

    public Response<AppointmentTimePoint> save(Authentication authentication, RoomAppointmentForm roomAppointmentForm) {
        Integer userId = Integer.valueOf(authentication.getName());
        AppointmentTimePoint appointmentTimePoint = new AppointmentTimePoint();
        DaoCommon.createDao(authentication, appointmentTimePoint);
        BeanUtils.copyProperties(roomAppointmentForm, appointmentTimePoint);
        appointmentTimePoint.setStartTime(TimePoint.getTimePointList().get(roomAppointmentForm.getStartTime()).getStartTime());
        appointmentTimePoint.setEndTime(TimePoint.getTimePointMap().get(roomAppointmentForm.getEndTime()).getEndTime());
        appointmentTimePoint.setUserId(userId);
        appointmentTimePoint.setStatus(0);
        List<Integer> currentTime = roomAppointmentForm.getCurrentTime().stream().distinct().collect(Collectors.toList());
        appointmentTimePoint.setTimePointIds(StringUtils.join(currentTime, ","));
        AppointmentTimePoint save = appointmentTimePointRepository.save(appointmentTimePoint);
        return ResponseUtils.success(save);
    }
}
