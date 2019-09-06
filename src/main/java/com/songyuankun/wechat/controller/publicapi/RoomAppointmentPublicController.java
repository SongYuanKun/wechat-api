package com.songyuankun.wechat.controller.publicapi;

import com.songyuankun.wechat.entity.TimePoint;
import com.songyuankun.wechat.service.RoomAppointmentServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("public/room_appointment")
@Slf4j
public class RoomAppointmentPublicController {

    private final RoomAppointmentServiceImpl roomAppointmentService;

    public RoomAppointmentPublicController(RoomAppointmentServiceImpl roomAppointmentService) {
        this.roomAppointmentService = roomAppointmentService;
    }

    @GetMapping("queryAppointmentTime")
    public List<TimePoint> queryEmptyTime(String date) {
        return roomAppointmentService.queryEmptyTimeTime(date);
    }


}
