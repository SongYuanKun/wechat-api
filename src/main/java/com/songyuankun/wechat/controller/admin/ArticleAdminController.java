package com.songyuankun.wechat.controller.admin;

import com.songyuankun.wechat.common.DaoCommon;
import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.Article;
import com.songyuankun.wechat.repository.ArticleRepository;
import com.songyuankun.wechat.request.ArticleForm;
import com.songyuankun.wechat.request.query.ArticleQuery;
import com.songyuankun.wechat.request.update.ArticleUpdateStatus;
import com.songyuankun.wechat.response.ArticleInfoResponse;
import com.songyuankun.wechat.service.ArticleServiceImpl;
import com.songyuankun.wechat.service.TagServiceImpl;
import com.songyuankun.wechat.util.WeChatUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author songyuankun
 */
@Api(value = "admin/article")
@RestController
@RequestMapping("admin/article")
@Slf4j
public class ArticleAdminController {
    private final ArticleServiceImpl articleService;
    private final ArticleRepository articleRepository;
    private final TagServiceImpl tagService;
    private final WeChatUtil weChatUtil;

    @Autowired
    public ArticleAdminController(ArticleServiceImpl articleService, ArticleRepository articleRepository, TagServiceImpl tagService, WeChatUtil weChatUtil) {
        this.articleService = articleService;
        this.articleRepository = articleRepository;
        this.tagService = tagService;
        this.weChatUtil = weChatUtil;
    }

    @ApiOperation("保存文章")
    @PostMapping("saveOrUpdate")
    public Response<Article> save(Authentication authentication, @RequestBody ArticleForm articleForm) {
        return ResponseUtils.success(articleService.saveOrUpdate(authentication, articleForm));
    }

    @ApiOperation("更新状态")
    @PostMapping("update/status")
    @Transactional(rollbackOn = Exception.class)
    public Response<Object> update(Authentication authentication, @RequestBody ArticleUpdateStatus articleUpdateStatus) {
        Article article = articleRepository.getOne(articleUpdateStatus.getId());
        if (articleUpdateStatus.getPublish() != null) {
            article.setPublish(articleUpdateStatus.getPublish());
        }
        if (articleUpdateStatus.getRecommend() != null) {
            article.setRecommend(articleUpdateStatus.getRecommend());
        }
        if (articleUpdateStatus.getTop() != null) {
            article.setTop(articleUpdateStatus.getTop());
        }
        DaoCommon.updateDao(authentication, article);
        articleRepository.save(article);
        return ResponseUtils.success();
    }

    @ApiOperation("发送到微信")
    @GetMapping("send2WeChat/{id}")
    @Transactional(rollbackOn = Exception.class)
    public Response<Object> send2WeChat(Authentication authentication, @PathVariable Integer id) {
        Article article = articleRepository.getOne(id);
        String mediaId = weChatUtil.addNews(Collections.singletonList(article));
        article.setMediaId(mediaId);
        DaoCommon.updateDao(authentication, article);
        articleRepository.save(article);
        return ResponseUtils.success();
    }

    @ApiOperation("批量发送到微信")
    @PostMapping("send2WeChat/ids")
    @Transactional(rollbackOn = Exception.class)
    public Response<Object> send2WeChat(Authentication authentication, @RequestBody Integer[] ids) {
        List<Article> articleList = articleRepository.queryAllByIdIn(Arrays.asList(ids));
        String mediaId = weChatUtil.addNews(articleList);
        for (Article article : articleList) {
            article.setMediaId(mediaId);
            DaoCommon.updateDao(authentication, article);
            articleRepository.save(article);
        }
        return ResponseUtils.success();
    }


    @ApiOperation("分页查询")
    @PostMapping("page")
    public Response<Page<ArticleInfoResponse>> page(@RequestBody ArticleQuery articleQuery) {
        Page<ArticleInfoResponse> page = articleService.page(articleQuery);
        return ResponseUtils.success(page);
    }


    @ApiOperation("已发布文章分页查询")
    @PostMapping("publicPage")
    public Response<Page<Article>> publicPage(@RequestBody ArticleQuery articleQuery) {
        Page<Article> articles = articleService.publicPage(articleQuery);
        return ResponseUtils.success(articles);
    }


    @ApiOperation("我创建的文章，分页")
    @PostMapping("my/create")
    public Response<Page<Article>> page(Authentication authentication, @RequestParam(required = false, defaultValue = "1") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Integer userId = Integer.valueOf(authentication.getName());
        Article article = new Article();
        article.setCreateUserId(userId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return ResponseUtils.success(articleRepository.findAll(Example.of(article), pageable));
    }


    @ApiOperation("获取博客详情")
    @GetMapping("info/{id}")
    public Response<ArticleInfoResponse> info(@PathVariable Integer id) {
        Article one = articleRepository.getOne(id);
        ArticleInfoResponse articleInfoResponse = new ArticleInfoResponse();
        BeanUtils.copyProperties(one, articleInfoResponse);
        articleInfoResponse.setTagList(tagService.getTagsByArticleId(id));
        return ResponseUtils.success(articleInfoResponse);
    }

    @ApiOperation("删除博客")
    @GetMapping("delete/{id}")
    public Response<ArticleInfoResponse> delete(@PathVariable Integer id) {
        articleRepository.deleteById(id);
        return ResponseUtils.success();
    }
}
