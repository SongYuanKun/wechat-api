package com.songyuankun.wechat.blog.controller;

import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.Recommend;
import com.songyuankun.wechat.service.RecommendServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author songyuankun
 */
@Api(tags = "推荐相关接口")
@RestController
@RequestMapping("blog/recommend")
@Slf4j
public class RecommendController {

    private final RecommendServiceImpl recommendService;


    public RecommendController(RecommendServiceImpl recommendService) {
        this.recommendService = recommendService;
    }

    @ApiOperation(value = "获取推荐列表", notes = "获取推荐列表")
    @GetMapping("recommends")
    public Response<List<Recommend>> recommends() {
        Page<Recommend> page = recommendService.findAllOrderByTop();
        return ResponseUtils.success(page.getContent());
    }
}
