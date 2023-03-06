package com.songyuankun.wechat.controller.publicapi;

import com.songyuankun.wechat.blog.event.NumberEventProducer;
import com.songyuankun.wechat.blog.application.ArticleApplicationService;
import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.ArticlePO;
import com.songyuankun.wechat.request.query.ArticleQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author songyuankun
 */

@RestController
@RequestMapping("public/article")
@Slf4j
public class ArticlePublicController {
    private final ArticleApplicationService articleService;
    private final NumberEventProducer numberEventProducer;

    public ArticlePublicController(ArticleApplicationService articleService, NumberEventProducer numberEventProducer) {
        this.articleService = articleService;
        this.numberEventProducer = numberEventProducer;
    }


    @GetMapping("getById")
    public Response<ArticlePO> getById(@RequestParam Integer id) {
        //读写分离处理
        numberEventProducer.onData("READ", id);
        return ResponseUtils.success(articleService.getOne(id));
    }

    @PostMapping("page")
    public Response<Page<ArticlePO>> publicPage(@RequestBody ArticleQuery articleQuery) {
        Page<ArticlePO> articles = articleService.publicPage(articleQuery);
        return ResponseUtils.success(articles);
    }
}
