package com.songyuankun.wechat.controller;

import com.songyuankun.wechat.common.DaoCommon;
import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.AppointmentTimePoint;
import com.songyuankun.wechat.entity.TimePoint;
import com.songyuankun.wechat.enums.AppointmentTimePointStatusEnum;
import com.songyuankun.wechat.repository.AppointmentTimePointRepository;
import com.songyuankun.wechat.request.RoomAppointmentForm;
import com.songyuankun.wechat.response.MyAppointmentTimeResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("room_appointment")
@Slf4j
public class RoomAppointmentController {

    private final AppointmentTimePointRepository appointmentTimePointRepository;

    public RoomAppointmentController(AppointmentTimePointRepository appointmentTimePointRepository) {
        this.appointmentTimePointRepository = appointmentTimePointRepository;
    }

    @PostMapping("save")
    public Response save(Authentication authentication, @RequestBody @Validated RoomAppointmentForm roomAppointmentForm) {
        Integer userId = Integer.valueOf(authentication.getName());
        AppointmentTimePoint appointmentTimePoint = new AppointmentTimePoint();
        DaoCommon.createDao(authentication, appointmentTimePoint);
        BeanUtils.copyProperties(roomAppointmentForm, appointmentTimePoint);
        appointmentTimePoint.setStartTime(TimePoint.MAP.get(roomAppointmentForm.getStartTime()).getStartTime());
        appointmentTimePoint.setEndTime(TimePoint.MAP.get(roomAppointmentForm.getEndTime()).getEndTime());
        appointmentTimePoint.setUserId(userId);
        appointmentTimePoint.setStatus(0);
        List<Integer> currentTime = roomAppointmentForm.getCurrentTime().stream().distinct().collect(Collectors.toList());
        appointmentTimePoint.setTimePointIds(StringUtils.join(currentTime, ","));
        AppointmentTimePoint save = appointmentTimePointRepository.save(appointmentTimePoint);
        return ResponseUtils.success(save);
    }

    @GetMapping("queryAppointmentTime")
    public List<TimePoint> queryEmptyTime(String date) {
        List<AppointmentTimePoint> appointmentTimePoints = appointmentTimePointRepository.findAllByDay(date);
        List<Integer> timePoints = new ArrayList<>();
        StringBuilder timePointString = new StringBuilder();
        if (!appointmentTimePoints.isEmpty()) {
            for (AppointmentTimePoint appointmentTimePoint : appointmentTimePoints) {
                timePointString.append(appointmentTimePoint.getTimePointIds()).append(",");
            }
            String[] split = timePointString.toString().split(",");
            for (String sp : split) {
                timePoints.add(Integer.parseInt(sp));
            }
        }
        List<TimePoint> all = new ArrayList<>(TimePoint.LIST);
        all.forEach(t -> t.setStatus(timePoints.contains(t.getId()) ? 1 : 0));
        return all;
    }

    @GetMapping("queryMyAppointment")
    public List<MyAppointmentTimeResponse> queryMyAppointment(Authentication authentication, @RequestParam(required = false, defaultValue = "0") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        List<MyAppointmentTimeResponse> responseList = new ArrayList<>();
        Integer userId = Integer.valueOf(authentication.getName());
        Sort sort = new Sort(Sort.Direction.DESC, "day", "startTime");
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        List<Integer> statusList = Arrays.asList(AppointmentTimePointStatusEnum.SUCCESS.getValue(), AppointmentTimePointStatusEnum.SIGN_IN.getValue());
        List<AppointmentTimePoint> appointmentTimePoints = appointmentTimePointRepository.findAllByUserIdAndStatusIn(userId, statusList, pageable);
        for (AppointmentTimePoint appointmentTimePoint : appointmentTimePoints) {
            MyAppointmentTimeResponse myAppointmentTimeResponse = new MyAppointmentTimeResponse();
            BeanUtils.copyProperties(appointmentTimePoint, myAppointmentTimeResponse);
            responseList.add(myAppointmentTimeResponse);
        }
        return responseList;
    }

}
