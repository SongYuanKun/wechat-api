package com.songyuankun.wechat.controller.blog;

import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.Article;
import com.songyuankun.wechat.event.NumberEventProducer;
import com.songyuankun.wechat.response.ArticleInfoResponse;
import com.songyuankun.wechat.service.ArticleServiceImpl;
import com.songyuankun.wechat.service.TagServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

/**
 * @author songyuankun
 */
@Api(tags = "文章相关接口")
@RestController
@RequestMapping("blog/article")
@Slf4j
public class ArticleBlogController {
    private final ArticleServiceImpl articleService;
    private final TagServiceImpl tagService;
    private final NumberEventProducer numberEventProducer;

    public ArticleBlogController(ArticleServiceImpl articleService, TagServiceImpl tagService, NumberEventProducer numberEventProducer) {
        this.articleService = articleService;
        this.tagService = tagService;
        this.numberEventProducer = numberEventProducer;
    }

    @ApiOperation(value = "获取文章详情", notes = "获取文章详情")
    @GetMapping("info/{id}")
    public Response<ArticleInfoResponse> info(@ApiParam("文章id") @PathVariable Integer id) {
        Article one = articleService.getOne(id);
        if (one != null) {
            //读写分离处理
            numberEventProducer.onData(1,id);
            ArticleInfoResponse articleInfoResponse = new ArticleInfoResponse();
            BeanUtils.copyProperties(one, articleInfoResponse);
            articleInfoResponse.setTagList(tagService.getTagsByArticleId(id));
            return ResponseUtils.success(articleInfoResponse);
        }else {
            return ResponseUtils.notFound();
        }
    }

    @ApiOperation(value = "获取文章列表", notes = "获取文章列表")
    @GetMapping("page")
    public Response<Page<Article>> publicPage(@ApiParam("页码") @RequestParam(required = false, defaultValue = "1") Integer pageNumber, @ApiParam("每页大小") @RequestParam(required = false, defaultValue = "10") Integer pageSize, @ApiParam("推荐") @RequestParam(required = false) Boolean recommend, @ApiParam("最新发布") @RequestParam(required = false) Boolean latest, @ApiParam("最多喜欢") @RequestParam(required = false) Boolean favorite) {
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

    @ApiOperation(value = "获取最热文章", notes = "获取最热文章")
    @GetMapping("hotReads")
    public Response<Page<Article>> hotReads() {
        return ResponseUtils.success(articleService.hotReads());
    }


    @ApiOperation(value = "文章点赞", notes = "文章点赞")
    @PutMapping("like/{id}")
    public Response<Object> likeArticle(@ApiParam("文章id") @PathVariable Integer id) {
        //读写分离处理
        numberEventProducer.onData(2,id);
        return ResponseUtils.success();
    }

}
