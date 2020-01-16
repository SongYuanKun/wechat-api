package com.songyuankun.wechat.controller.admin;

import com.songyuankun.wechat.common.DaoCommon;
import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.Recommend;
import com.songyuankun.wechat.request.RecommendForm;
import com.songyuankun.wechat.request.query.RecommendQuery;
import com.songyuankun.wechat.service.RecommendServiceImpl;
import io.swagger.annotations.Api;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * @author songyuankun
 */
@Api(value = "admin/recommend")
@RestController
@RequestMapping("admin/recommend")
public class RecommendAdminController {
    private final RecommendServiceImpl recommendService;

    public RecommendAdminController(RecommendServiceImpl recommendService) {
        this.recommendService = recommendService;
    }

    /**
     * 列表
     */
    @PostMapping("/page")
    public Response list(@RequestBody RecommendQuery recommendQuery) {
        return ResponseUtils.success(recommendService.findAll(recommendQuery));
    }

    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public Response info(@PathVariable("id") Integer id) {
        return ResponseUtils.success(recommendService.getOne(id));
    }

    /**
     * 保存
     */
    @PostMapping("/saveOrUpdate")
    public Response save(Authentication authentication, @RequestBody RecommendForm recommendForm) {
        Recommend recommend = new Recommend();
        BeanUtils.copyProperties(recommendForm, recommend);
        if (recommendForm.getId() == null) {
            DaoCommon.createDao(authentication, recommend);
        } else {
            recommend = recommendService.getOne(recommend.getId());
            DaoCommon.updateDao(authentication, recommend);
        }
        return ResponseUtils.success(recommendService.save(recommend));
    }


    @GetMapping("/top/{id}")
    public Response updateTop(@PathVariable Integer id) {
        recommendService.updateTop(id);
        return ResponseUtils.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public Response delete(@RequestBody Integer[] ids) {
        recommendService.removeByIds(Arrays.asList(ids));
        return ResponseUtils.success();
    }

    @GetMapping("/delete/{id}")
    public Response delete(@PathVariable Integer id) {
        recommendService.removeById(id);
        return ResponseUtils.success();
    }
}
