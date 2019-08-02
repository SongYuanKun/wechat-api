package com.songyuankun.wechat.controller.admin;

import com.songyuankun.wechat.common.DaoCommon;
import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.dao.AppointmentTimePoint;
import com.songyuankun.wechat.dao.TimePoint;
import com.songyuankun.wechat.repository.AppointmentTimePointRepository;
import com.songyuankun.wechat.request.RoomAppointmentForm;
import com.songyuankun.wechat.request.query.RoomAppointmentQuery;
import com.songyuankun.wechat.response.MyAppointmentTimeResponse;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.Predicate;
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
            DaoCommon.createDao(authentication, appointmentTimePoint);
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

    @PostMapping("queryAppointmentList")
    public PageImpl<MyAppointmentTimeResponse> queryAppointmentList(@Validated @RequestBody RoomAppointmentQuery roomAppointmentQuery) {
        List<MyAppointmentTimeResponse> responseList = new ArrayList<>();
        String username = roomAppointmentQuery.getUserName();
        String day = roomAppointmentQuery.getDay();
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(roomAppointmentQuery.getPageNumber() - 1, roomAppointmentQuery.getPageSize(), sort);
        Page<AppointmentTimePoint> appointmentTimePoints = appointmentTimePointRepository.findAll((
                (root, query, cb) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (!StringUtils.isEmpty(username)) {
                        predicates.add(cb.like(root.get("userName"), username));
                    }
                    if (!StringUtils.isEmpty(day)) {
                        predicates.add(cb.equal(root.get("day"), day));
                    }
                    return cb.and(predicates.toArray(new Predicate[0]));
                }), pageable);
        for (AppointmentTimePoint appointmentTimePoint : appointmentTimePoints) {
            MyAppointmentTimeResponse myAppointmentTimeResponse = new MyAppointmentTimeResponse();
            BeanUtils.copyProperties(appointmentTimePoint, myAppointmentTimeResponse);
            myAppointmentTimeResponse.setTimePoint(TimePoint.MAP.get(appointmentTimePoint.getTimePointId()));
            responseList.add(myAppointmentTimeResponse);
        }
        return new PageImpl<>(responseList, pageable, appointmentTimePoints.getTotalElements());
    }

    @GetMapping("changeStatus")
    public AppointmentTimePoint changeStatus(Authentication authentication,@RequestParam Integer id, @RequestParam Integer status) {
        AppointmentTimePoint appointmentTimePoint = appointmentTimePointRepository.getOne(id);
        appointmentTimePoint.setStatus(status);
        DaoCommon.updateDao(authentication, appointmentTimePoint);
        return appointmentTimePointRepository.save(appointmentTimePoint);
    }

}
