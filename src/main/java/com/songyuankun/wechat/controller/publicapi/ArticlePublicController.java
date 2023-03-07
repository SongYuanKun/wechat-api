package com.songyuankun.wechat.controller.publicapi;

import com.songyuankun.wechat.blog.application.ArticleApplicationService;
import com.songyuankun.wechat.cache.ArticleCache;
import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.infrastructure.dataaccess.ArticlePO;
import com.songyuankun.wechat.request.query.ArticleQuery;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author songyuankun
 */

@RestController
@RequestMapping("public/article")
@Slf4j
public class ArticlePublicController {
    private final ArticleApplicationService articleService;
    private final ArticleCache articleCache;

    public ArticlePublicController(ArticleApplicationService articleService, ArticleCache articleCache) {
        this.articleService = articleService;
        this.articleCache = articleCache;
    }


    @GetMapping("getById")
    public Response<ArticlePO> getById(@RequestParam Integer id) {
        ArticlePO one = articleService.getOne(id);
        articleCache.incrementReadCount(id);
        return ResponseUtils.success(one);
    }

    @PostMapping("page")
    public Response<Page<ArticlePO>> publicPage(@RequestBody ArticleQuery articleQuery) {
        Page<ArticlePO> articles = articleService.publicPage(articleQuery);
        return ResponseUtils.success(articles);
    }
}
