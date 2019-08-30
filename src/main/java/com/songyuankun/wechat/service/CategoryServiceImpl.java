package com.songyuankun.wechat.service;


import com.google.common.collect.Lists;
import com.songyuankun.wechat.entity.Category;
import com.songyuankun.wechat.repository.CategoryRepository;
import com.songyuankun.wechat.request.query.CategoryQuery;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author songyuankun
 */
@Service
public class CategoryServiceImpl {
    private CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<Category> findRootByNameAndType(CategoryQuery categoryQuery) {
        Pageable pageable = PageRequest.of(categoryQuery.getPageNumber() - 1, categoryQuery.getPageSize());

        Page<Category> all = categoryRepository.findAll((categoryRoot, criteriaQuery, criteriaBuilder)
                -> {
            String name = categoryQuery.getName();
            Integer type = categoryQuery.getType();
            List<Predicate> list = Lists.newArrayList();
            if (StringUtils.isNotEmpty(name)) {
                list.add(criteriaBuilder.like(categoryRoot.get("name").as(String.class), "%" + name + "%"));
            }
            if (ObjectUtils.isNotEmpty(type)) {
                list.add(criteriaBuilder.equal(categoryRoot.get("type").as(Integer.class), type));
            }
            list.add(criteriaBuilder.equal(categoryRoot.get("parentId").as(Integer.class), -1));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
        List<Integer> parentIdList = all.stream().map(Category::getId).distinct().collect(Collectors.toList());
        all.getContent().addAll(getChildrenList(parentIdList));
        return all;
    }

    public List<Category> getChildrenList(List<Integer> categoryIdList) {
        List<Category> all = categoryRepository.findAll((categoryRoot, criteriaQuery, criteriaBuilder)
                -> {
            List<Predicate> list = Lists.newArrayList();
            CriteriaBuilder.In<Integer> in = criteriaBuilder.in(categoryRoot.get("parentId").as(Integer.class));
            categoryIdList.forEach(in::value);
            list.add(criteriaBuilder.and(in));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        });

        if (!all.isEmpty()) {
            List<Integer> parentIdList = all.stream().map(Category::getId).distinct().collect(Collectors.toList());
            getChildrenList(parentIdList);
            all.addAll(getChildrenList(parentIdList));
        }

        return all;

    }


}
