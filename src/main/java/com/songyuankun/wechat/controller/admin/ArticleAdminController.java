package com.songyuankun.wechat.controller.admin;

import com.songyuankun.wechat.blog.domain.repository.ArticleRepository;
import com.songyuankun.wechat.blog.application.ArticleApplicationService;
import com.songyuankun.wechat.common.DaoCommon;
import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.infrastructure.dataaccess.ArticlePO;
import com.songyuankun.wechat.request.ArticlePOForm;
import com.songyuankun.wechat.request.query.ArticleQuery;
import com.songyuankun.wechat.request.update.ArticleUpdateStatus;
import com.songyuankun.wechat.response.ArticlePOInfoResponse;
import com.songyuankun.wechat.service.TagServiceImpl;
import com.songyuankun.wechat.util.WeChatUtil;
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

@RestController
@RequestMapping("admin/article")
@Slf4j
public class ArticleAdminController {
    private final ArticleApplicationService articleService;
    private final ArticleRepository articleRepository;
    private final TagServiceImpl tagService;
    private final WeChatUtil weChatUtil;

    @Autowired
    public ArticleAdminController(ArticleApplicationService articleService, ArticleRepository articleRepository, TagServiceImpl tagService, WeChatUtil weChatUtil) {
        this.articleService = articleService;
        this.articleRepository = articleRepository;
        this.tagService = tagService;
        this.weChatUtil = weChatUtil;
    }


    @PostMapping("saveOrUpdate")
    public Response<ArticlePO> save(Authentication authentication, @RequestBody ArticlePOForm articleForm) {
        return ResponseUtils.success(articleService.saveOrUpdate(authentication, articleForm));
    }


    @PostMapping("update/status")
    @Transactional(rollbackOn = Exception.class)
    public Response<Object> update(Authentication authentication, @RequestBody ArticleUpdateStatus articleUpdateStatus) {
        ArticlePO articlePO = articleRepository.getOne(articleUpdateStatus.getId());
        if (articleUpdateStatus.getPublish() != null) {
            articlePO.setPublish(articleUpdateStatus.getPublish());
        }
        if (articleUpdateStatus.getRecommend() != null) {
            articlePO.setRecommend(articleUpdateStatus.getRecommend());
        }
        if (articleUpdateStatus.getTop() != null) {
            articlePO.setTop(articleUpdateStatus.getTop());
        }
        DaoCommon.updateDao(authentication, articlePO);
        articleRepository.save(articlePO);
        return ResponseUtils.success();
    }


    @GetMapping("send2WeChat/{id}")
    @Transactional(rollbackOn = Exception.class)
    public Response<Object> send2WeChat(Authentication authentication, @PathVariable Integer id) {
        ArticlePO articlePO = articleRepository.getOne(id);
        String mediaId = weChatUtil.addNews(Collections.singletonList(articlePO));
        articlePO.setMediaId(mediaId);
        DaoCommon.updateDao(authentication, articlePO);
        articleRepository.save(articlePO);
        return ResponseUtils.success();
    }


    @PostMapping("send2WeChat/ids")
    @Transactional(rollbackOn = Exception.class)
    public Response<Object> send2WeChat(Authentication authentication, @RequestBody Integer[] ids) {
        List<ArticlePO> articlePOList = articleRepository.queryAllByIdIn(Arrays.asList(ids));
        String mediaId = weChatUtil.addNews(articlePOList);
        for (ArticlePO articlePO : articlePOList) {
            articlePO.setMediaId(mediaId);
            DaoCommon.updateDao(authentication, articlePO);
            articleRepository.save(articlePO);
        }
        return ResponseUtils.success();
    }


    @PostMapping("page")
    public Response<Page<ArticlePOInfoResponse>> page(@RequestBody ArticleQuery articleQuery) {
        Page<ArticlePOInfoResponse> page = articleService.page(articleQuery);
        return ResponseUtils.success(page);
    }


    @PostMapping("publicPage")
    public Response<Page<ArticlePO>> publicPage(@RequestBody ArticleQuery articleQuery) {
        Page<ArticlePO> articles = articleService.publicPage(articleQuery);
        return ResponseUtils.success(articles);
    }


    @PostMapping("my/create")
    public Response<Page<ArticlePO>> page(Authentication authentication, @RequestParam(required = false, defaultValue = "1") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Integer userId = Integer.valueOf(authentication.getName());
        ArticlePO articlePO = new ArticlePO();
        articlePO.setCreateUserId(userId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return ResponseUtils.success(articleRepository.findAll(Example.of(articlePO), pageable));
    }


    @GetMapping("info/{id}")
    public Response<ArticlePOInfoResponse> info(@PathVariable Integer id) {
        ArticlePO one = articleRepository.getOne(id);
        ArticlePOInfoResponse articleInfoResponse = new ArticlePOInfoResponse();
        BeanUtils.copyProperties(one, articleInfoResponse);
        articleInfoResponse.setTagList(tagService.getTagsByArticleId(id));
        return ResponseUtils.success(articleInfoResponse);
    }


    @GetMapping("delete/{id}")
    public Response<ArticlePOInfoResponse> delete(@PathVariable Integer id) {
        articleRepository.deleteById(id);
        return ResponseUtils.success();
    }
}
