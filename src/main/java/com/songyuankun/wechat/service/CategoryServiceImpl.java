package com.songyuankun.wechat.service;


import com.google.common.collect.Lists;
import com.songyuankun.wechat.entity.Category;
import com.songyuankun.wechat.repository.CategoryRepository;
import com.songyuankun.wechat.request.query.CategoryQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author songyuankun
 */
@Service
public class CategoryServiceImpl {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Page<Category> findRootByNameAndType(CategoryQuery categoryQuery) {
        Pageable pageable = PageRequest.of(categoryQuery.getPageNumber() - 1, categoryQuery.getPageSize());

        return categoryRepository.findAll((categoryRoot, criteriaQuery, criteriaBuilder)
                -> {
            String name = categoryQuery.getName();
            Integer type = categoryQuery.getType();
            List<Predicate> list = Lists.newArrayList();
            if (StringUtils.isNotEmpty(name)) {
                list.add(criteriaBuilder.like(categoryRoot.get("name").as(String.class), "%" + name + "%"));
            }
            if (type != null) {
                list.add(criteriaBuilder.equal(categoryRoot.get("type").as(Integer.class), type));
            }
            list.add(criteriaBuilder.equal(categoryRoot.get("parentId").as(Integer.class), -1));
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
    }

    public List<Category> getChildrenList(List<Integer> categoryIdList) {
        List<Category> childrenList = new ArrayList<>();
        if (categoryIdList.isEmpty()) {
            return childrenList;
        }
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
            childrenList.addAll(getChildrenList(parentIdList));
            childrenList.addAll(all);
        }
        return childrenList;
    }


    String renderCategoryArr(String categoryIds, List<Category> categoryList) {
        if (org.springframework.util.StringUtils.isEmpty(categoryIds)) {
            return "";
        }
        List<String> categoryStrList;
        String[] categoryIdArr = categoryIds.split(",");
        // 根据Id查找类别名称
        categoryStrList = Arrays.stream(categoryIdArr).map(Integer::parseInt).map(categoryId -> categoryList
                .stream()
                .filter(category -> category.getId().equals(categoryId))
                .map(Category::getName)
                .findAny().orElse("类别已被删除")).collect(Collectors.toList());
        return String.join(",", categoryStrList);

    }

    public List<Category> select(Integer type) {
        Category category = new Category();
        category.setType(type);
        List<Category> categoryList = categoryRepository.findAll(Example.of(category));
        //添加顶级分类
        Category root = new Category();
        root.setId(-1);
        root.setName("根目录");
        root.setParentId(-1);
        categoryList.add(root);
        return categoryList;
    }

}
