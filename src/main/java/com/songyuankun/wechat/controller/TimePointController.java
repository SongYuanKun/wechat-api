package com.songyuankun.wechat.controller;

import com.songyuankun.wechat.dao.TimePoint;
import com.songyuankun.wechat.repository.TimePointRepository;
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
@RequestMapping("time_point")
@Slf4j
public class TimePointController {
    private final TimePointRepository timePointRepository;

    public TimePointController(TimePointRepository timePointRepository) {
        this.timePointRepository = timePointRepository;
    }


    @GetMapping("getAll")
    public List<TimePoint> all() {
        return timePointRepository.findAll();
    }


}
