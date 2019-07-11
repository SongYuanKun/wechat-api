package com.songyuankun.wechat.controller;

import com.songyuankun.wechat.dao.RoomAppointment;
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

    @GetMapping("getRoomAppointmentList")
    public List<RoomAppointment> getRoomAppointmentList(@RequestParam(value = "roomId") Integer roomId) {
        RoomAppointment roomAppointment = new RoomAppointment();
        roomAppointment.setRoomId(roomId);
        return roomAppointmentRepository.findAll(Example.of(roomAppointment));
    }


}
