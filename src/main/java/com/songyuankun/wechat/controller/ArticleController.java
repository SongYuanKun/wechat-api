package com.songyuankun.wechat.controller;

import com.songyuankun.wechat.common.DaoCommon;
import com.songyuankun.wechat.entity.Article;
import com.songyuankun.wechat.repository.ArticleRepository;
import com.songyuankun.wechat.request.ArticleForm;
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
import java.util.List;

/**
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("article")
@Slf4j
public class ArticleController {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
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

    @GetMapping("public/getById")
    public Article getById(@RequestParam Integer id) {
        return articleRepository.getOne(id);
    }

    @PostMapping("public/all")
    public List<Article> publicAll() {
        Article article = new Article();
        return articleRepository.findAll(Example.of(article));
    }

    @PostMapping("all")
    public List<Article> all() {
        return articleRepository.findAll();
    }

    @PostMapping("page")
    public Page<Article> page(@RequestParam(required = false, defaultValue = "0") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return articleRepository.findAll(pageable);
    }

    @PostMapping("public/page")
    public Page<Article> publicPage(@RequestParam(required = false, defaultValue = "0") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Article article = new Article();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return articleRepository.findAll(Example.of(article), pageable);
    }

    @PostMapping("my/create")
    public Page<Article> page(Authentication authentication, @RequestParam(required = false, defaultValue = "0") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Article article = new Article();
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return articleRepository.findAll(Example.of(article), pageable);
    }
}
