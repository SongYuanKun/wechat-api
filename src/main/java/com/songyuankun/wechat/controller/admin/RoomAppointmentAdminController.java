package com.songyuankun.wechat.controller.admin;

import com.songyuankun.wechat.common.DaoCommon;
import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.entity.AppointmentTimePoint;
import com.songyuankun.wechat.entity.TimePoint;
import com.songyuankun.wechat.repository.AppointmentTimePointRepository;
import com.songyuankun.wechat.request.RoomAppointmentForm;
import com.songyuankun.wechat.request.query.RoomAppointmentQuery;
import com.songyuankun.wechat.response.MyAppointmentTimeResponse;
import com.songyuankun.wechat.service.RoomAppointmentServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("admin/room_appointment")
@Slf4j
public class RoomAppointmentAdminController {

    private final AppointmentTimePointRepository appointmentTimePointRepository;
    private final RoomAppointmentServiceImpl roomAppointmentService;

    public RoomAppointmentAdminController(AppointmentTimePointRepository appointmentTimePointRepository, RoomAppointmentServiceImpl roomAppointmentService) {
        this.appointmentTimePointRepository = appointmentTimePointRepository;
        this.roomAppointmentService = roomAppointmentService;
    }

    @PostMapping("save")
    public Response save(Authentication authentication, @RequestBody @Validated RoomAppointmentForm roomAppointmentForm) {
        return roomAppointmentService.save(authentication, roomAppointmentForm);
    }

    @GetMapping("queryAppointmentTime")
    public List<TimePoint> queryEmptyTime(String date) {
        return roomAppointmentService.queryEmptyTimeTime(date);
    }

    @PostMapping("queryAppointmentList")
    public PageImpl<MyAppointmentTimeResponse> queryAppointmentList(@Validated @RequestBody RoomAppointmentQuery roomAppointmentQuery) {
        List<MyAppointmentTimeResponse> responseList = new ArrayList<>();
        String username = roomAppointmentQuery.getUserName();
        String day = roomAppointmentQuery.getDay();
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(roomAppointmentQuery.getPageNumber() - 1, roomAppointmentQuery.getPageSize(), sort);
        Page<AppointmentTimePoint> appointmentTimePoints = appointmentTimePointRepository.findAll((
                (root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (!StringUtils.isEmpty(username)) {
                        predicates.add(criteriaBuilder.like(root.get("userName"), username));
                    }
                    if (!StringUtils.isEmpty(day)) {
                        predicates.add(criteriaBuilder.equal(root.get("day"), day));
                    }
                    return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
                }), pageable);
        for (AppointmentTimePoint appointmentTimePoint : appointmentTimePoints) {
            MyAppointmentTimeResponse myAppointmentTimeResponse = new MyAppointmentTimeResponse();
            BeanUtils.copyProperties(appointmentTimePoint, myAppointmentTimeResponse);
            myAppointmentTimeResponse.setTimeValue(appointmentTimePoint.getStartTime() + "-" + appointmentTimePoint.getEndTime());
            responseList.add(myAppointmentTimeResponse);
        }
        return new PageImpl<>(responseList, pageable, appointmentTimePoints.getTotalElements());
    }

    @GetMapping("changeStatus")
    public AppointmentTimePoint changeStatus(Authentication authentication, @RequestParam Integer id, @RequestParam Integer status) {
        AppointmentTimePoint appointmentTimePoint = appointmentTimePointRepository.getOne(id);
        appointmentTimePoint.setStatus(status);
        DaoCommon.updateDao(authentication, appointmentTimePoint);
        return appointmentTimePointRepository.save(appointmentTimePoint);
    }

}
