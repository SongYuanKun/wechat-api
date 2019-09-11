package com.songyuankun.wechat.controller.admin;

import com.songyuankun.wechat.common.DaoCommon;
import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.Article;
import com.songyuankun.wechat.entity.Category;
import com.songyuankun.wechat.repository.ArticleRepository;
import com.songyuankun.wechat.repository.CategoryRepository;
import com.songyuankun.wechat.request.ArticleForm;
import com.songyuankun.wechat.request.update.ArticleUpdateStatus;
import com.songyuankun.wechat.response.ArticleInfoResponse;
import com.songyuankun.wechat.service.CategoryServiceImpl;
import com.songyuankun.wechat.service.TagServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("admin/article")
@Slf4j
public class ArticleAdminController {
    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository;
    private final TagServiceImpl tagService;
    private final CategoryServiceImpl categoryService;

    @Autowired
    public ArticleAdminController(ArticleRepository articleRepository, CategoryRepository categoryRepository, TagServiceImpl tagService, CategoryServiceImpl categoryService) {
        this.articleRepository = articleRepository;
        this.categoryRepository = categoryRepository;
        this.tagService = tagService;
        this.categoryService = categoryService;
    }

    @PostMapping("saveOrUpdate")
    public Article save(Authentication authentication, @RequestBody ArticleForm articleForm) {
        Article article = new Article();
        BeanUtils.copyProperties(articleForm, article);
        if (article.getId() == null) {
            DaoCommon.createDao(authentication, article);
        } else {
            article = articleRepository.getOne(article.getId());
        DaoCommon.updateDao(authentication, article);
    }
        tagService.saveTagAndNew(articleForm.getTagList(), article.getId());
        articleRepository.save(article);
        return article;
    }

    @PostMapping("update/status")
    @Transactional(rollbackOn = Exception.class)
    public Response update(Authentication authentication, @RequestBody ArticleUpdateStatus articleUpdateStatus) {
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

    @PostMapping("page")
    public Page<ArticleInfoResponse> page(@RequestParam(required = false, defaultValue = "1") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        Page<Article> all = articleRepository.findAll(pageable);
        List<Category> collect = categoryRepository.findAll().stream().filter(category -> category.getType().equals(0)).collect(Collectors.toList());
        List<ArticleInfoResponse> articleInfoResponseList = new ArrayList<>();
        all.stream().forEach(article -> {
            ArticleInfoResponse articleInfoResponse = new ArticleInfoResponse();
            BeanUtils.copyProperties(article, articleInfoResponse);
            articleInfoResponse.setCategoryListStr(categoryService.renderCategoryArr(article.getCategoryId(), collect));
            articleInfoResponse.setTagList(tagService.getTagsByArticleId(article.getId()));
            articleInfoResponseList.add(articleInfoResponse);
        });
        return new PageImpl<>(articleInfoResponseList, pageable, all.getTotalElements());
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

    @GetMapping("delete/{id}")
    public Response<ArticleInfoResponse> delete(@PathVariable Integer id) {
        articleRepository.deleteById(id);
        return ResponseUtils.success();
    }
}
