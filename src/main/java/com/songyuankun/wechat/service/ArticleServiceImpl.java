package com.songyuankun.wechat.service;


import com.google.common.collect.Lists;
import com.songyuankun.wechat.common.DaoCommon;
import com.songyuankun.wechat.entity.Article;
import com.songyuankun.wechat.entity.Category;
import com.songyuankun.wechat.repository.ArticleRepository;
import com.songyuankun.wechat.request.ArticleForm;
import com.songyuankun.wechat.request.query.ArticleQuery;
import com.songyuankun.wechat.response.ArticleInfoResponse;
import com.songyuankun.wechat.util.WeChatUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author songyuankun
 */
@Service
public class ArticleServiceImpl {
    private final CategoryServiceImpl categoryService;
    private final TagServiceImpl tagService;
    private final WeChatUtil weChatUtil;
    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository, CategoryServiceImpl categoryService, TagServiceImpl tagService, WeChatUtil weChatUtil) {
        this.articleRepository = articleRepository;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.weChatUtil = weChatUtil;
    }

    public Article saveOrUpdate(Authentication authentication, ArticleForm articleForm) {
        Article article = new Article();
        if (articleForm.getId() == null) {
            BeanUtils.copyProperties(articleForm, article);
            String mediaId = weChatUtil.addThumbMedia(article.getCover());
            articleForm.setThumbMediaId(mediaId);
            DaoCommon.createDao(authentication, article);
        } else {
            article = articleRepository.getOne(articleForm.getId());
            if (StringUtils.isEmpty(article.getThumbMediaId()) || !article.getCover().equals(articleForm.getCover())) {
                String mediaId = weChatUtil.addThumbMedia(article.getCover());
                articleForm.setThumbMediaId(mediaId);
            }
            BeanUtils.copyProperties(articleForm, article);
            DaoCommon.updateDao(authentication, article);
        }

        Article save = articleRepository.save(article);
        tagService.saveTagAndNew(articleForm.getTagList(), save.getId());
        return save;
    }


    public Page<Article> publicPage(ArticleQuery articleQuery) {
        Pageable pageable = PageRequest.of(articleQuery.getPageNumber() - 1, articleQuery.getPageSize());
        return findAll(articleQuery, pageable);
    }

    public Page<Article> hotReads() {
        Sort sort = Sort.by(Sort.Order.desc("readNum"));
        Pageable pageable = PageRequest.of(0, 10, sort);
        return articleRepository.findAll(pageable);
    }

    public Page<ArticleInfoResponse> page(ArticleQuery articleQuery) {
        Pageable pageable = PageRequest.of(articleQuery.getPageNumber() - 1, articleQuery.getPageSize());
        Article article1 = new Article();
        article1.setMediaId(articleQuery.getMediaId());
        Page<Article> all = articleRepository.findAll(Example.of(article1), pageable);
        List<Category> categoryList = categoryService.select(0);
        List<ArticleInfoResponse> articleInfoResponseList = new ArrayList<>();
        all.stream().forEach(article -> {
            ArticleInfoResponse articleInfoResponse = new ArticleInfoResponse();
            BeanUtils.copyProperties(article, articleInfoResponse);
            articleInfoResponse.setCategoryListStr(categoryService.renderCategoryArr(article.getCategoryId(), categoryList));
            articleInfoResponse.setTagList(tagService.getTagsByArticleId(article.getId()));
            articleInfoResponseList.add(articleInfoResponse);
        });
        return new PageImpl<>(articleInfoResponseList, pageable, all.getTotalElements());
    }

    public Article getOne(Integer id) {
        return articleRepository.queryFirstById(id);
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


    public Page<Article> findAll(Example<Article> example, Pageable pageable) {
        return articleRepository.findAll(example, pageable);
    }


    public void updateLikeNum(Integer id) {
        articleRepository.updateLikeNum(id);
    }

    public void updateReadNum(Integer id) {
        articleRepository.updateReadNum(id);
    }

}
