package com.songyuankun.wechat.controller.blog;

import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.Timeline;
import com.songyuankun.wechat.service.TimelineServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author songyuankun
 */
@Api(tags = "")
@RestController
@RequestMapping("blog/timeline")
public class TimelineController {

    @Resource
    private TimelineServiceImpl timelineService;

    @GetMapping
    public Response<List<Timeline>> listTimeline() {
        return ResponseUtils.success(timelineService.listTimeLine());
    }
}
