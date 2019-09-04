package com.songyuankun.wechat.controller.admin;

import com.songyuankun.wechat.common.DaoCommon;
import com.songyuankun.wechat.common.MyException;
import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.Category;
import com.songyuankun.wechat.enums.CategoryRankEnum;
import com.songyuankun.wechat.repository.CategoryRepository;
import com.songyuankun.wechat.request.CategoryForm;
import com.songyuankun.wechat.request.query.CategoryQuery;
import com.songyuankun.wechat.response.CategoryPageResponse;
import com.songyuankun.wechat.service.CategoryServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    private final CategoryServiceImpl categoryService;

    public CategoryAdminController(CategoryRepository categoryRepository, CategoryServiceImpl categoryService) {
        this.categoryRepository = categoryRepository;
        this.categoryService = categoryService;
    }

    @PostMapping("saveOrUpdate")
    public Response save(Authentication authentication, @RequestBody CategoryForm categoryForm) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryForm, category);
        verifyCategory(category);
        if (category.getId() == null) {
            DaoCommon.createDao(authentication, category);
        } else {
            category = categoryRepository.getOne(category.getId());
            DaoCommon.updateDao(authentication, category);
        }
        categoryRepository.save(category);
        return ResponseUtils.success();

    }

    @PostMapping("page")
    public Response<CategoryPageResponse> page(@RequestBody CategoryQuery categoryQuery) {
        CategoryPageResponse categoryPageResponse = new CategoryPageResponse();
        Page<Category> rootByNameAndType = categoryService.findRootByNameAndType(categoryQuery);
        List<Integer> parentIdList = rootByNameAndType.stream().map(Category::getId).distinct().collect(Collectors.toList());
        List<Category> childrenList = categoryService.getChildrenList(parentIdList);
        categoryPageResponse.setCategoryPage(rootByNameAndType);
        categoryPageResponse.setChildrenList(childrenList);
        return ResponseUtils.success(categoryPageResponse);
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

    /**
     * 数据校验
     *
     * @param category category
     */
    private void verifyCategory(Category category) {
        //上级分类级别
        int parentRank = CategoryRankEnum.ROOT.getValue();
        if (category.getParentId() != CategoryRankEnum.FIRST.getValue()
                && category.getParentId() != CategoryRankEnum.ROOT.getValue()) {
            Category parentCategory = categoryRepository.getOne(category.getParentId());
            parentRank = parentCategory.getRank();
        }
        // 一级
        if (category.getRank() == CategoryRankEnum.FIRST.getValue() && category.getParentId() != CategoryRankEnum.ROOT.getValue()) {
            throw new MyException("上级目录只能为根目录");
        }

        //二级
        if (category.getRank() == CategoryRankEnum.SECOND.getValue() && parentRank != CategoryRankEnum.FIRST.getValue()) {
            throw new MyException("上级目录只能为一级类型");
        }

        //三级
        if (category.getRank() == CategoryRankEnum.THIRD.getValue() && parentRank != CategoryRankEnum.SECOND.getValue()) {
            throw new MyException("上级目录只能为二级类型");
        }
    }
}
