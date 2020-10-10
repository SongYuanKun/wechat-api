package com.songyuankun.wechat.controller.blog;

import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.Timeline;
import com.songyuankun.wechat.service.TimelineServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author songyuankun
 */
@Api(tags = "时间轴接口")
@RestController
@RequestMapping("blog/timeline")
public class TimelineController {

    private final TimelineServiceImpl timelineService;

    public TimelineController(TimelineServiceImpl timelineService) {
        this.timelineService = timelineService;
    }

    @ApiOperation(value = "获取时间轴", notes = "获取时间轴")
    @GetMapping
    public Response<List<Timeline>> listTimeline() {
        return ResponseUtils.success(timelineService.listTimeLine());
    }
}
