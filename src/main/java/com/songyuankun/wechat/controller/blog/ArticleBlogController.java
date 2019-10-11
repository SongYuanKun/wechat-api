package com.songyuankun.wechat.controller.blog;

import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.Article;
import com.songyuankun.wechat.response.ArticleInfoResponse;
import com.songyuankun.wechat.service.ArticleServiceImpl;
import com.songyuankun.wechat.service.TagServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

/**
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("blog/article")
@Slf4j
public class ArticleBlogController {
    private final ArticleServiceImpl articleService;
    private final TagServiceImpl tagService;

    @Autowired
    public ArticleBlogController(ArticleServiceImpl articleService, TagServiceImpl tagService) {
        this.articleService = articleService;
        this.tagService = tagService;
    }

    @GetMapping("info/{id}")
    public Response<ArticleInfoResponse> info(@PathVariable Integer id) {
        articleService.updateReadNum(id);
        Article one = articleService.getOne(id);
        ArticleInfoResponse articleInfoResponse = new ArticleInfoResponse();
        BeanUtils.copyProperties(one, articleInfoResponse);
        articleInfoResponse.setTagList(tagService.getTagsByArticleId(id));
        return ResponseUtils.success(articleInfoResponse);
    }

    @GetMapping("page")
    public Response publicPage(@RequestParam(required = false, defaultValue = "1") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize, @RequestParam(required = false) Boolean recommend, @RequestParam(required = false) Boolean latest,@RequestParam(required = false) Boolean favorite) {
        Article article = new Article();
        article.setPublish(true);
        if (recommend != null && recommend) {
            article.setRecommend(true);
        }
        Sort sort = Sort.by(Sort.Order.desc("top"));
        if (latest != null && latest) {
            Sort.Order createTime = Sort.Order.desc("createTime");
            sort = sort.and(Sort.by(createTime));
        }
        if (favorite != null && favorite) {
            Sort.Order likeNum = Sort.Order.desc("likeNum");
            sort = sort.and(Sort.by(likeNum));
        }
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        Page<Article> all = articleService.findAll(Example.of(article), pageable);
        return ResponseUtils.success(all);
    }

    @GetMapping("hotReads")
    public Response<Page<Article>> hotReads() {
        return ResponseUtils.success(articleService.hotReads());
    }


    @PutMapping("like/{id}")
    public Response likeArticle(@PathVariable Integer id) {
        articleService.updateLikeNum(id);
        return ResponseUtils.success();
    }

}
