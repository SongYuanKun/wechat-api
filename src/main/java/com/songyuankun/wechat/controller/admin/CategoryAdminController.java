package com.songyuankun.wechat.controller.admin;

import com.songyuankun.wechat.common.DaoCommon;
import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.Category;
import com.songyuankun.wechat.repository.CategoryRepository;
import com.songyuankun.wechat.request.ArticleForm;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类接口
 *
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("admin/category")
@Slf4j
public class CategoryAdminController {
    private final CategoryRepository categoryRepository;

    public CategoryAdminController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @PostMapping("saveOrUpdate")
    public Category save(Authentication authentication, @RequestBody ArticleForm articleForm) {
        Category category = new Category();
        BeanUtils.copyProperties(articleForm, category);
        if (category.getId() == null) {
            DaoCommon.createDao(authentication, category);
        }
        categoryRepository.save(category);
        return category;
    }

    @PostMapping("page")
    public Page<Category> page(@RequestParam(required = false, defaultValue = "0") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return categoryRepository.findAll(pageable);
    }


    @GetMapping("list")
    public Response<List<Category>> list() {
        return ResponseUtils.success(categoryRepository.findAll());
    }


}
