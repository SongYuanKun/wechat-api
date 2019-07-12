package com.songyuankun.wechat.controller;

import com.songyuankun.wechat.dao.AppointmentTimePoint;
import com.songyuankun.wechat.dao.RoomAppointment;
import com.songyuankun.wechat.dao.TimePoint;
import com.songyuankun.wechat.repository.AppointmentTimePointRepository;
import com.songyuankun.wechat.repository.RoomAppointmentRepository;
import com.songyuankun.wechat.request.RoomAppointmentForm;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    private final RoomAppointmentRepository roomAppointmentRepository;
    private final AppointmentTimePointRepository appointmentTimePointRepository;

    public RoomAppointmentController(RoomAppointmentRepository roomAppointmentRepository, AppointmentTimePointRepository appointmentTimePointRepository) {
        this.roomAppointmentRepository = roomAppointmentRepository;
        this.appointmentTimePointRepository = appointmentTimePointRepository;
    }

    @PostMapping("save")
    public RoomAppointment save(Authentication authentication, @RequestBody RoomAppointmentForm roomAppointmentForm) {
        Integer userId = Integer.valueOf(authentication.getName());
        RoomAppointment roomAppointment = new RoomAppointment();
        BeanUtils.copyProperties(roomAppointmentForm, roomAppointment);
        roomAppointment.setUserId(userId);
        roomAppointment.setStatus(0);
        roomAppointmentRepository.save(roomAppointment);
        return roomAppointment;
    }

    @GetMapping("public/getById")
    public RoomAppointment getById(@RequestParam Integer id) {
        return roomAppointmentRepository.getOne(id);
    }

    @PostMapping("public/page")
    public Page<RoomAppointment> page(@RequestParam(required = false, defaultValue = "0") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return roomAppointmentRepository.findAll(pageable);
    }

    @GetMapping("listByRoomId")
    public List<RoomAppointment> listByRoomId(@RequestParam(value = "roomId") Integer roomId) {
        RoomAppointment roomAppointment = new RoomAppointment();
        roomAppointment.setRoomId(roomId);
        return roomAppointmentRepository.findAll(Example.of(roomAppointment));
    }

    @GetMapping("listByUserId")
    public List<RoomAppointment> listByUserId(Authentication authentication) {
        RoomAppointment roomAppointment = new RoomAppointment();
        Integer userId = Integer.valueOf(authentication.getName());
        roomAppointment.setUserId(userId);
        return roomAppointmentRepository.findAll(Example.of(roomAppointment));
    }

    @GetMapping("queryUsefulEndTime")
    public List<TimePoint> queryUsefulEndTime(String date, Integer startTimePointId) {
        List<AppointmentTimePoint> appointmentTimePoints = appointmentTimePointRepository.queryAllByDay(date);
        List<TimePoint> result = new ArrayList<>();
        List<TimePoint> all = new ArrayList<>();

        for (TimePoint timePoint : all) {
            int id = timePoint.getId();
            if (!appointmentTimePoints.contains(id)) {
                result.add(timePoint);
            } else {
                break;
            }
        }
        return result;
    }

    @GetMapping("queryEmptyTime")
    public List<TimePoint> queryEmptyTime(String date) {
        List<AppointmentTimePoint> appointmentTimePoints = appointmentTimePointRepository.queryAllByDay(date);
        List<Integer> timePoints = appointmentTimePoints.stream().map(AppointmentTimePoint::getTimePointId).collect(Collectors.toList());
        List<TimePoint> all = new ArrayList<>();
        return all.stream().filter(t -> !timePoints.contains(t.getId())).collect(Collectors.toList());
    }

}
