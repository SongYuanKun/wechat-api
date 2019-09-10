package com.songyuankun.wechat.controller.admin;

import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.Recommend;
import com.songyuankun.wechat.request.RecommendForm;
import com.songyuankun.wechat.request.query.RecommendQuery;
import com.songyuankun.wechat.service.RecommendServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;


/**
 * @author songyuankun
 */
@RestController
@RequestMapping("/admin/recommend")
public class RecommendAdminController {
    private final RecommendServiceImpl recommendService;

    public RecommendAdminController(RecommendServiceImpl recommendService) {
        this.recommendService = recommendService;
    }

    /**
     * 列表
     */
    @PostMapping("/list")
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
    @PostMapping("/save")
    public Response save(@RequestBody RecommendForm recommendForm) {

        return ResponseUtils.success(recommendService.save(recommendForm));
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Response update(@RequestBody RecommendForm recommendForm) {
        Recommend one = recommendService.getOne(recommendForm.getId());
        BeanUtils.copyProperties(recommendForm, one);
        return ResponseUtils.success(recommendService.save(one));
    }

    @PutMapping("/top/{id}")
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
}
