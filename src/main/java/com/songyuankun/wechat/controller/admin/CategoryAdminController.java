package com.songyuankun.wechat.controller.admin;

import com.songyuankun.wechat.common.DaoCommon;
import com.songyuankun.wechat.common.MyException;
import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.Category;
import com.songyuankun.wechat.enums.CategoryRankEnum;
import com.songyuankun.wechat.repository.CategoryRepository;
import com.songyuankun.wechat.request.CategoryForm;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
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
    public Page<Category> page(@RequestParam(required = false, defaultValue = "0") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return categoryRepository.findAll(pageable);
    }


    @GetMapping("list")
    public Response<List<Category>> list() {
        return ResponseUtils.success(categoryRepository.findAll());
    }

    @GetMapping("select")
    public Response<List<Category>> select(Integer type) {
        Category category = new Category();
        category.setType(type);
        List<Category> categoryList = categoryRepository.findAll(Example.of(category));
        //添加顶级分类
        Category root = new Category();
        root.setId(-1);
        root.setName("根目录");
        root.setParentId(-1);
        categoryList.add(root);
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
     * @param category
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
