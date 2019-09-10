package com.songyuankun.wechat.controller.publicapi;

import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.request.query.RecommendQuery;
import com.songyuankun.wechat.service.RecommendServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author songyuankun
 */
@RestController
@RequestMapping("/admin/recommend")
public class RecommendPublicController {
    private final RecommendServiceImpl recommendService;

    public RecommendPublicController(RecommendServiceImpl recommendService) {
        this.recommendService = recommendService;
    }

    /**
     * 列表
     */
    @PostMapping("/recommends")
    public Response list(@RequestBody RecommendQuery recommendQuery) {
        return ResponseUtils.success(recommendService.findAll(recommendQuery));
    }

}
