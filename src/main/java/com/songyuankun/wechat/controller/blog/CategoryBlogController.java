package com.songyuankun.wechat.controller.blog;

import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.Category;
import com.songyuankun.wechat.repository.CategoryRepository;
import com.songyuankun.wechat.service.CategoryServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "blog/category")
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

    @ApiOperation("分类详情")
    @GetMapping("info/{id}")
    public Response<Category> info(@PathVariable Integer id) {
        return ResponseUtils.success(categoryRepository.getOne(id));
    }

    @ApiOperation("分类列表")
    @GetMapping("select")
    public Response<List<Category>> select(Integer type) {
        List<Category> categoryList = categoryService.select(type);
        return ResponseUtils.success(categoryList);
    }
}
