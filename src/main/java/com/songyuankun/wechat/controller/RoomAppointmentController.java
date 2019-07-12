package com.songyuankun.wechat.controller;

import com.songyuankun.wechat.dao.RoomAppointment;
import com.songyuankun.wechat.repository.RoomAppointmentRepository;
import com.songyuankun.wechat.request.RoomAppointmentForm;
import com.songyuankun.wechat.util.DateUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("room_appointment")
@Slf4j
public class RoomAppointmentController {

    private final RoomAppointmentRepository roomAppointmentRepository;

    public RoomAppointmentController(RoomAppointmentRepository roomAppointmentRepository) {
        this.roomAppointmentRepository = roomAppointmentRepository;
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

    @GetMapping("queryIsEmptyTime")
    public boolean queryIsEmptyTime(String date, String startTime, String endTime) {
        LocalDateTime startDateTime = LocalDateTime.parse(date + " " + startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime endDateTime = LocalDateTime.parse(date + " " + endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        log.info(startDateTime.toString() + "==" + endDateTime.toString());
        List<RoomAppointment> startTimeBetween = roomAppointmentRepository.queryAllByStartTimeBetween(DateUtil.fromLocalDateTime(startDateTime), DateUtil.fromLocalDateTime(endDateTime));
        List<RoomAppointment> endTimeBetween = roomAppointmentRepository.queryAllByEndTimeBetween(DateUtil.fromLocalDateTime(startDateTime), DateUtil.fromLocalDateTime(endDateTime));
        return endTimeBetween.isEmpty() && startTimeBetween.isEmpty();
    }

    @GetMapping("queryEmptyTime")
    public boolean queryEmptyTime(String date, String startTime, String endTime) {
        LocalDateTime startDateTime = LocalDateTime.parse(date + " " + startTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime endDateTime = LocalDateTime.parse(date + " " + endTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        log.info(startDateTime.toString() + "==" + endDateTime.toString());
        List<RoomAppointment> startTimeBetween = roomAppointmentRepository.queryAllByStartTimeBetween(DateUtil.fromLocalDateTime(startDateTime), DateUtil.fromLocalDateTime(endDateTime));
        List<RoomAppointment> endTimeBetween = roomAppointmentRepository.queryAllByEndTimeBetween(DateUtil.fromLocalDateTime(startDateTime), DateUtil.fromLocalDateTime(endDateTime));
        return endTimeBetween.isEmpty() && startTimeBetween.isEmpty();
    }

}
