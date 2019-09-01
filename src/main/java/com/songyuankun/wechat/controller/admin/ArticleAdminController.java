package com.songyuankun.wechat.controller.admin;

import com.songyuankun.wechat.common.DaoCommon;
import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.Article;
import com.songyuankun.wechat.repository.ArticleRepository;
import com.songyuankun.wechat.request.ArticleForm;
import com.songyuankun.wechat.response.ArticleInfoResponse;
import com.songyuankun.wechat.service.TagServiceImpl;
import io.swagger.annotations.Api;
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

/**
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("admin/article")
@Slf4j
public class ArticleAdminController {
    private final ArticleRepository articleRepository;
    private final TagServiceImpl tagService;

    @Autowired
    public ArticleAdminController(ArticleRepository articleRepository, TagServiceImpl tagService) {
        this.articleRepository = articleRepository;
        this.tagService = tagService;
    }

    @PostMapping("saveOrUpdate")
    public Article save(Authentication authentication, @RequestBody ArticleForm articleForm) {
        Article article = new Article();
        BeanUtils.copyProperties(articleForm, article);
        if (article.getId() == null) {
            DaoCommon.createDao(authentication, article);
        }
        articleRepository.save(article);
        return article;
    }

    @PostMapping("update")
    @Transactional(rollbackOn = Exception.class)
    public Article update(Authentication authentication, @RequestBody ArticleForm articleForm) {
        Article article = new Article();
        BeanUtils.copyProperties(articleForm, article);
        DaoCommon.updateDao(authentication, article);
        return articleRepository.save(article);
    }

    @PostMapping("page")
    public Page<Article> page(@RequestParam(required = false, defaultValue = "1") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return articleRepository.findAll(pageable);
    }

    @PostMapping("my/create")
    public Page<Article> page(Authentication authentication, @RequestParam(required = false, defaultValue = "1") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Integer userId = Integer.valueOf(authentication.getName());
        Article article = new Article();
        article.setCreateUserId(userId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return articleRepository.findAll(Example.of(article), pageable);
    }


    @GetMapping("info/{id}")
    public Response<ArticleInfoResponse> info(@PathVariable Integer id) {
        Article one = articleRepository.getOne(id);
        ArticleInfoResponse articleInfoResponse = new ArticleInfoResponse();
        BeanUtils.copyProperties(one, articleInfoResponse);
        articleInfoResponse.setTagList(tagService.getTagsByArticleId(id));
        return ResponseUtils.success(articleInfoResponse);
    }
}
