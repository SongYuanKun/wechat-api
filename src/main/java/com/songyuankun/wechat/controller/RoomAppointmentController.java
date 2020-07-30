package com.songyuankun.wechat.controller;

import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.entity.AppointmentTimePoint;
import com.songyuankun.wechat.enums.AppointmentTimePointStatusEnum;
import com.songyuankun.wechat.repository.AppointmentTimePointRepository;
import com.songyuankun.wechat.request.RoomAppointmentForm;
import com.songyuankun.wechat.request.query.BasePageQuery;
import com.songyuankun.wechat.response.MyAppointmentTimeResponse;
import com.songyuankun.wechat.service.RoomAppointmentServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("room_appointment")
@Slf4j
public class RoomAppointmentController {

    private final AppointmentTimePointRepository appointmentTimePointRepository;
    private final RoomAppointmentServiceImpl roomAppointmentService;

    public RoomAppointmentController(AppointmentTimePointRepository appointmentTimePointRepository, RoomAppointmentServiceImpl roomAppointmentService) {
        this.appointmentTimePointRepository = appointmentTimePointRepository;
        this.roomAppointmentService = roomAppointmentService;
    }

    @PostMapping("save")
    public Response save(Authentication authentication, @RequestBody @Validated RoomAppointmentForm roomAppointmentForm) {
        return roomAppointmentService.save(authentication, roomAppointmentForm);
    }

    @PostMapping("queryMyAppointment")
    public List<MyAppointmentTimeResponse> queryMyAppointment(Authentication authentication, @RequestBody BasePageQuery myAppointmentQuery) {
        List<MyAppointmentTimeResponse> responseList = new ArrayList<>();
        Integer userId = Integer.valueOf(authentication.getName());
        Sort sort = Sort.by(Sort.Direction.DESC, "day", "startTime");
        Pageable pageable = PageRequest.of(myAppointmentQuery.getPageNumber() - 1, myAppointmentQuery.getPageSize(), sort);
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
