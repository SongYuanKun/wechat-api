package com.songyuankun.wechat.controller.publicapi;

import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.Article;
import com.songyuankun.wechat.request.query.ArticleQuery;
import com.songyuankun.wechat.service.ArticleServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("public/article")
@Slf4j
public class ArticlePublicController {
    private final ArticleServiceImpl articleService;

    public ArticlePublicController(ArticleServiceImpl articleService) {
        this.articleService = articleService;
    }


    @GetMapping("getById")
    public Response<Article> getById(@RequestParam Integer id) {
        return ResponseUtils.success(articleService.getOne(id));
    }

    @PostMapping("page")
    public Response<Page<Article>> publicPage(@RequestBody ArticleQuery articleQuery) {
        Page<Article> articles = articleService.publicPage(articleQuery);
        return ResponseUtils.success(articles);
    }
}
