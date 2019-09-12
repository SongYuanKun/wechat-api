package com.songyuankun.wechat.controller.blog;

import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.Article;
import com.songyuankun.wechat.repository.ArticleRepository;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("blog/article")
@Slf4j
public class ArticleBlogController {
    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleBlogController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("getById")
    public Article getById(@RequestParam Integer id) {
        return articleRepository.getOne(id);
    }

    @GetMapping("page")
    public Response publicPage(@RequestParam(required = false, defaultValue = "1") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize, @RequestParam(required = false) Boolean recommend, @RequestParam(required = false) Boolean latest) {
        Article article = new Article();
        if (recommend != null && recommend) {
            article.setRecommend(true);
        }
        Sort sort = Sort.by(Sort.Order.desc("top"));
        if (latest != null && latest) {
            Sort.Order createTime = Sort.Order.desc("createTime");
            sort = sort.and(Sort.by(createTime));
        }

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);


        Page<Article> all = articleRepository.findAll(Example.of(article), pageable);
        return ResponseUtils.success(all);
    }
}
