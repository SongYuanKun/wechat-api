package com.songyuankun.wechat.blog.controller;

import com.songyuankun.wechat.blog.application.ArticleApplicationService;
import com.songyuankun.wechat.blog.application.query.FindArticleListQuery;
import com.songyuankun.wechat.cache.ArticleCache;
import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.infrastructure.dataaccess.ArticlePO;
import com.songyuankun.wechat.response.ArticlePOInfoResponse;
import com.songyuankun.wechat.service.TagServiceImpl;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author songyuankun
 */

@RestController
@RequestMapping("blog/article")
@Slf4j
public class ArticleBlogController {
    private final ArticleApplicationService articleService;
    private final TagServiceImpl tagService;
    private final ArticleCache articleCache;

    public ArticleBlogController(ArticleApplicationService articleService, TagServiceImpl tagService, ArticleCache articleCache) {
        this.articleService = articleService;
        this.tagService = tagService;
        this.articleCache = articleCache;
    }


    @GetMapping("info/{id}")
    public Response<ArticlePOInfoResponse> info(@PathVariable Integer id) {
        ArticlePO one = articleService.getOne(id);
        if (one != null) {
            //读写分离处理
            ArticlePOInfoResponse articleInfoResponse = new ArticlePOInfoResponse();
            BeanUtils.copyProperties(one, articleInfoResponse);
            articleInfoResponse.setTagList(tagService.getTagsByArticleId(id));
            articleCache.incrementReadCount(id);
            return ResponseUtils.success(articleInfoResponse);
        } else {
            return ResponseUtils.notFound();
        }
    }


    @GetMapping("page")
    public Response<Page<ArticlePO>> publicPage(
        FindArticleListQuery findArticleListQuery
    ) {
        ArticlePO articlePO = new ArticlePO();
        articlePO.setPublish(true);
        if (BooleanUtils.isTrue(findArticleListQuery.getRecommend())) {
            articlePO.setRecommend(true);
        }
        Sort sort = Sort.by(Sort.Order.desc("top"));
        if (BooleanUtils.isTrue(findArticleListQuery.getLatest())) {
            Sort.Order createTime = Sort.Order.desc("createTime");
            sort = sort.and(Sort.by(createTime));
        }
        if (BooleanUtils.isTrue(findArticleListQuery.getFavorite())) {
            Sort.Order likeNum = Sort.Order.desc("likeNum");
            sort = sort.and(Sort.by(likeNum));
        }

        Pageable pageable = PageRequest.of(findArticleListQuery.getPageNumber() - 1, findArticleListQuery.getPageSize(), sort);
        Page<ArticlePO> all = articleService.findAll(Example.of(articlePO), pageable);
        return ResponseUtils.success(all);
    }


    @GetMapping("hotReads")
    public Response<Page<ArticlePO>> hotReads() {
        return ResponseUtils.success(articleService.hotReads());
    }


    @PutMapping("like/{id}")
    public Response<Object> likeArticle(@PathVariable Integer id) {
        //读写分离处理
        articleCache.incrementLikeCount(id);
        return ResponseUtils.success();
    }

}
