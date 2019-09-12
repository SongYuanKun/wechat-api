package com.songyuankun.wechat.controller.blog;

import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.entity.Tag;
import com.songyuankun.wechat.request.query.TagQuery;
import com.songyuankun.wechat.service.TagServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("blog/tag")
@Slf4j
public class TagController {

    private final TagServiceImpl tagService;


    public TagController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }


    @GetMapping("tags")
    public Response recommends() {
        TagQuery tagQuery = new TagQuery();
        tagQuery.setPageNumber(1);
        tagQuery.setPageSize(10);
        Page<Tag> page = tagService.findAllByQuery(tagQuery);
        return ResponseUtils.success(page.getContent());
    }
}
