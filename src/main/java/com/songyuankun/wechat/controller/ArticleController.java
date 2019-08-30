package com.songyuankun.wechat.controller;

import com.songyuankun.wechat.entity.Article;
import com.songyuankun.wechat.repository.ArticleRepository;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("public/getById")
    public Article getById(@RequestParam Integer id) {
        return articleRepository.getOne(id);
    }

    @PostMapping("public/page")
    public Page<Article> publicPage(@RequestParam(required = false, defaultValue = "1") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Article article = new Article();
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize);
        return articleRepository.findAll(Example.of(article), pageable);
    }
}
