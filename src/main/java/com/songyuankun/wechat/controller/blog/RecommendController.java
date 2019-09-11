package com.songyuankun.wechat.controller.blog;

import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.Recommend;
import com.songyuankun.wechat.service.RecommendServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("blog/recommend")
@Slf4j
public class RecommendController {

    private final RecommendServiceImpl recommendService;


    public RecommendController(RecommendServiceImpl recommendService) {
        this.recommendService = recommendService;
    }


    @GetMapping("recommends")
    public Response recommends() {
        Page<Recommend> page = recommendService.findAllOrderByTop();
        return ResponseUtils.success(page.getContent());
    }
}
