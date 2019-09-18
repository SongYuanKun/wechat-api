package com.songyuankun.wechat.service;


import com.google.common.collect.Lists;
import com.songyuankun.wechat.entity.Article;
import com.songyuankun.wechat.entity.Category;
import com.songyuankun.wechat.repository.ArticleRepository;
import com.songyuankun.wechat.repository.CategoryRepository;
import com.songyuankun.wechat.request.query.ArticleQuery;
import com.songyuankun.wechat.response.ArticleInfoResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author songyuankun
 */
@Service
public class ArticleServiceImpl {
    private final CategoryServiceImpl categoryService;
    private final TagServiceImpl tagService;
    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, CategoryServiceImpl categoryService, TagServiceImpl tagService, CategoryRepository categoryRepository) {
        this.articleRepository = articleRepository;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.categoryRepository = categoryRepository;
    }

    public Page<Article> publicPage(ArticleQuery articleQuery) {
        Pageable pageable = PageRequest.of(articleQuery.getPageNumber() - 1, articleQuery.getPageSize());
        return findAll(articleQuery,pageable);
    }

    public Page<Article> hotReads() {
        Sort sort = Sort.by(Sort.Order.desc("readNum"));
        Pageable pageable = PageRequest.of(0, 10, sort);
        return articleRepository.findAll(pageable);
    }

    public Page<ArticleInfoResponse> page(ArticleQuery articleQuery) {
        Pageable pageable = PageRequest.of(articleQuery.getPageNumber() - 1, articleQuery.getPageSize());
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

    public Article getOne(Integer id) {
        return articleRepository.getOne(id);
    }

    public Page<Article> findAll(ArticleQuery articleQuery, Pageable pageable) {
        return articleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            String title = articleQuery.getTitle();
            List<Predicate> list = Lists.newArrayList();
            if (StringUtils.isNotEmpty(title)) {
                list.add(criteriaBuilder.like(root.get("title").as(String.class), "%" + title + "%"));
            }
            list.add(criteriaBuilder.isTrue(root.get("publish").as(Boolean.class)));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
    }

}
