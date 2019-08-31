package com.songyuankun.wechat.service;


import com.google.common.collect.Lists;
import com.songyuankun.wechat.entity.Tag;
import com.songyuankun.wechat.repository.TagRepository;
import com.songyuankun.wechat.request.query.TagQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * @author songyuankun
 */
@Service
public class TagServiceImpl {
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
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
}
