package com.songyuankun.wechat.controller.admin;

import com.songyuankun.wechat.common.DaoCommon;
import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.Tag;
import com.songyuankun.wechat.request.TagForm;
import com.songyuankun.wechat.request.query.TagQuery;
import com.songyuankun.wechat.service.TagServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类接口
 *
 * @author songyuankun
 */

@RestController
@RequestMapping("admin/tag")
@Slf4j
public class TagAdminController {
    private final TagServiceImpl tagService;

    public TagAdminController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }

    @PostMapping("saveOrUpdate")
    public Response save(Authentication authentication, @RequestBody TagForm tagForm) {
        Tag tag = new Tag();
        BeanUtils.copyProperties(tagForm, tag);
        if (tag.getId() == null) {
            DaoCommon.createDao(authentication, tag);
        } else {
            tag = tagService.getOne(tag.getId());
            DaoCommon.updateDao(authentication, tag);
        }
        tagService.save(tag);
        return ResponseUtils.success();

    }

    @PostMapping("page")
    public Response<Page<Tag>> page(@RequestBody TagQuery tagQuery) {
        Page<Tag> allByQuery = tagService.findAllByQuery(tagQuery);
        return ResponseUtils.success(allByQuery);
    }


    @GetMapping("info/{id}")
    public Response<Tag> info(@PathVariable Integer id) {
        return ResponseUtils.success(tagService.getOne(id));
    }

    @GetMapping("select")
    public Response<List<Tag>> select(Integer type) {
        Tag tag = new Tag();
        tag.setType(type);
        List<Tag> tagList = tagService.findAll(Example.of(tag));
        return ResponseUtils.success(tagList);
    }

    @GetMapping("delete/{id}")
    public Response delete(@PathVariable Integer id) {
        tagService.deleteById(id);
        return ResponseUtils.success(null);
    }
}
