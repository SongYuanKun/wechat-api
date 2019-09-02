package com.songyuankun.wechat.service;


import com.google.common.collect.Lists;
import com.songyuankun.wechat.entity.Tag;
import com.songyuankun.wechat.entity.TagLink;
import com.songyuankun.wechat.repository.TagLinkRepository;
import com.songyuankun.wechat.repository.TagRepository;
import com.songyuankun.wechat.request.query.TagQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author songyuankun
 */
@Service
public class TagServiceImpl {
    private final TagRepository tagRepository;
    private final TagLinkRepository tagLinkRepository;

    public TagServiceImpl(TagRepository tagRepository, TagLinkRepository tagLinkRepository) {
        this.tagRepository = tagRepository;
        this.tagLinkRepository = tagLinkRepository;
    }

    public Page<Tag> findAllByQuery(TagQuery tagQuery) {
        Pageable pageable = PageRequest.of(tagQuery.getPageNumber() - 1, tagQuery.getPageSize());

        return tagRepository.findAll((root, criteriaQuery, criteriaBuilder)
                -> {
            String name = tagQuery.getName();
            List<Predicate> list = Lists.newArrayList();
            if (StringUtils.isNotEmpty(name)) {
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + name + "%"));
            }
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
    }

    public List<Tag> getTagsByArticleId(Integer articleId) {
        List<Integer> idList = tagLinkRepository.findAllByArticleId(articleId).stream().map(TagLink::getTagId).collect(Collectors.toList());
        if (idList.isEmpty()) {
            return new ArrayList<>();
        }
        return tagRepository.findAll(((root, criteriaQuery, criteriaBuilder) -> {
            CriteriaBuilder.In<Integer> in = criteriaBuilder.in(root.get("id").as(Integer.class));
            idList.forEach(in::value);
            List<Predicate> list = Lists.newArrayList();
            list.add(in);
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }));
    }

    public void saveTagAndNew(List<Tag> tagList, Integer id) {
        tagList.forEach(tag -> {
            if (tag.getId() == null) {
                tagRepository.save(tag);
            }
            TagLink tagLink = new TagLink(id, tag.getId());
            tagLinkRepository.save(tagLink);
        });
    }
}
