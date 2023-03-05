package com.songyuankun.wechat.blog.application;


import com.songyuankun.wechat.blog.dao.ArticleRepository;
import com.songyuankun.wechat.common.DaoCommon;
import com.songyuankun.wechat.entity.ArticlePO;
import com.songyuankun.wechat.entity.Category;
import com.songyuankun.wechat.request.ArticlePOForm;
import com.songyuankun.wechat.request.query.ArticleQuery;
import com.songyuankun.wechat.response.ArticlePOInfoResponse;
import com.songyuankun.wechat.service.CategoryServiceImpl;
import com.songyuankun.wechat.service.TagServiceImpl;
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
public class ArticleServiceImpl implements ArticleService {
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

    public ArticlePO saveOrUpdate(Authentication authentication, ArticlePOForm articleForm) {
        ArticlePO articlePO = new ArticlePO();
        if (articleForm.getId() == null) {
            BeanUtils.copyProperties(articleForm, articlePO);
            String mediaId = weChatUtil.addThumbMedia(articlePO.getCover());
            articleForm.setThumbMediaId(mediaId);
            DaoCommon.createDao(authentication, articlePO);
        } else {
            articlePO = articleRepository.getOne(articleForm.getId());
            if (StringUtils.isEmpty(articlePO.getThumbMediaId()) || !articlePO.getCover().equals(articleForm.getCover())) {
                String mediaId = weChatUtil.addThumbMedia(articlePO.getCover());
                articleForm.setThumbMediaId(mediaId);
            }
            BeanUtils.copyProperties(articleForm, articlePO);
            DaoCommon.updateDao(authentication, articlePO);
        }

        ArticlePO save = articleRepository.save(articlePO);
        tagService.saveTagAndNew(articleForm.getTagList(), save.getId());
        return save;
    }


    public Page<ArticlePO> publicPage(ArticleQuery articleQuery) {
        Pageable pageable = PageRequest.of(articleQuery.getPageNumber() - 1, articleQuery.getPageSize());
        return findAll(articleQuery, pageable);
    }

    public Page<ArticlePO> hotReads() {
        Sort sort = Sort.by(Sort.Order.desc("readNum"));
        Pageable pageable = PageRequest.of(0, 10, sort);
        return articleRepository.findAll(pageable);
    }

    public Page<ArticlePOInfoResponse> page(ArticleQuery articleQuery) {
        Pageable pageable = PageRequest.of(articleQuery.getPageNumber() - 1, articleQuery.getPageSize());
        ArticlePO articlePO1 = new ArticlePO();
        articlePO1.setMediaId(articleQuery.getMediaId());
        Page<ArticlePO> all = articleRepository.findAll(Example.of(articlePO1), pageable);
        List<Category> categoryList = categoryService.select(0);
        List<ArticlePOInfoResponse> articleInfoResponseList = new ArrayList<>();
        all.stream().forEach(article -> {
            ArticlePOInfoResponse articleInfoResponse = new ArticlePOInfoResponse();
            BeanUtils.copyProperties(article, articleInfoResponse);
            articleInfoResponse.setCategoryListStr(categoryService.renderCategoryArr(article.getCategoryId(), categoryList));
            articleInfoResponse.setTagList(tagService.getTagsByArticleId(article.getId()));
            articleInfoResponseList.add(articleInfoResponse);
        });
        return new PageImpl<>(articleInfoResponseList, pageable, all.getTotalElements());
    }

    public ArticlePO getOne(Integer id) {
        return articleRepository.queryFirstById(id);
    }

    public Page<ArticlePO> findAll(ArticleQuery articleQuery, Pageable pageable) {
        return articleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            String title = articleQuery.getTitle();
            List<Predicate> list = new ArrayList<>();
            if (StringUtils.isNotEmpty(title)) {
                list.add(criteriaBuilder.like(root.get("title").as(String.class), "%" + title + "%"));
            }
            list.add(criteriaBuilder.isTrue(root.get("publish").as(Boolean.class)));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
    }


    public Page<ArticlePO> findAll(Example<ArticlePO> example, Pageable pageable) {
        return articleRepository.findAll(example, pageable);
    }


    public void updateLikeNum(Integer id) {
        articleRepository.updateLikeNum(id);
    }

    public void updateReadNum(Integer id) {
        articleRepository.updateReadNum(id);
    }

}
