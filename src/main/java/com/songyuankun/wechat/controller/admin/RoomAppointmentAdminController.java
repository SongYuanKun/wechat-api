package com.songyuankun.wechat.controller.admin;

import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.dao.AppointmentTimePoint;
import com.songyuankun.wechat.dao.TimePoint;
import com.songyuankun.wechat.repository.AppointmentTimePointRepository;
import com.songyuankun.wechat.request.RoomAppointmentForm;
import com.songyuankun.wechat.response.MyAppointmentTimeResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("admin/room_appointment")
@Slf4j
public class RoomAppointmentAdminController {

    private final AppointmentTimePointRepository appointmentTimePointRepository;

    public RoomAppointmentAdminController(AppointmentTimePointRepository appointmentTimePointRepository) {
        this.appointmentTimePointRepository = appointmentTimePointRepository;
    }

    @PostMapping("save")
    public Response save(Authentication authentication, @RequestBody @Validated RoomAppointmentForm roomAppointmentForm) {
        Integer userId = Integer.valueOf(authentication.getName());
        AppointmentTimePoint save = null;
        for (Integer integer : roomAppointmentForm.getCurrentTime()) {
            AppointmentTimePoint appointmentTimePoint = new AppointmentTimePoint();
            appointmentTimePoint.setDay(roomAppointmentForm.getDay());
            appointmentTimePoint.setUserId(userId);
            appointmentTimePoint.setTimePointId(integer);
            save = appointmentTimePointRepository.save(appointmentTimePoint);
        }
        return ResponseUtils.success(save);
    }

    @GetMapping("queryAppointmentTime")
    public List<TimePoint> queryEmptyTime(String date) {
        List<AppointmentTimePoint> appointmentTimePoints = appointmentTimePointRepository.findAllByDay(date);
        List<Integer> timePoints = appointmentTimePoints.stream().map(AppointmentTimePoint::getTimePointId).collect(Collectors.toList());
        List<TimePoint> all = new ArrayList<>();
        BeanUtils.copyProperties(TimePoint.LIST, all);
        all.forEach(t -> t.setStatus(timePoints.contains(t.getId()) ? 1 : 0));
        return all;
    }

    @GetMapping("queryAppointmentList")
    public PageImpl<MyAppointmentTimeResponse> queryAppointmentList(@RequestParam(required = false, defaultValue = "0") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        List<MyAppointmentTimeResponse> responseList = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<AppointmentTimePoint> appointmentTimePoints = appointmentTimePointRepository.findAll(pageable);
        for (AppointmentTimePoint appointmentTimePoint : appointmentTimePoints) {
            MyAppointmentTimeResponse myAppointmentTimeResponse = new MyAppointmentTimeResponse();
            myAppointmentTimeResponse.setDay(appointmentTimePoint.getDay());
            myAppointmentTimeResponse.setUserName(appointmentTimePoint.getUserName());
            myAppointmentTimeResponse.setPhone(appointmentTimePoint.getPhone());
            myAppointmentTimeResponse.setStatus(appointmentTimePoint.getStatus());
            myAppointmentTimeResponse.setTimePointId(appointmentTimePoint.getTimePointId());
            myAppointmentTimeResponse.setTimePoint(TimePoint.MAP.get(appointmentTimePoint.getTimePointId()));
            responseList.add(myAppointmentTimeResponse);
        }
        return new PageImpl<>(responseList, pageable, appointmentTimePoints.getTotalElements());
    }

}
