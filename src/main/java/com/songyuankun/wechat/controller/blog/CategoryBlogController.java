package com.songyuankun.wechat.controller.blog;

import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.Category;
import com.songyuankun.wechat.repository.CategoryRepository;
import com.songyuankun.wechat.service.CategoryServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类接口
 *
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("blog/category")
@Slf4j
public class CategoryBlogController {
    private final CategoryRepository categoryRepository;
    private final CategoryServiceImpl categoryService;

    public CategoryBlogController(CategoryRepository categoryRepository, CategoryServiceImpl categoryService) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }

    @GetMapping("info/{id}")
    public Response<Category> info(@PathVariable Integer id) {
        return ResponseUtils.success(categoryRepository.getOne(id));
    }

    @GetMapping("select")
    public Response<List<Category>> select(Integer type) {
        List<Category> categoryList = categoryService.select(type);
        return ResponseUtils.success(categoryList);
    }

    @GetMapping("delete/{id}")
    public Response delete(@PathVariable Integer id) {
        categoryRepository.deleteById(id);
        return ResponseUtils.success(null);
    }
}
