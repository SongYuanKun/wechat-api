package com.songyuankun.wechat.controller.blog;

import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.request.query.TagQuery;
import com.songyuankun.wechat.response.TagResponse;
import com.songyuankun.wechat.service.TagServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author songyuankun
 */
@Api(tags = "标签相关接口")
@RestController
@RequestMapping("blog/tag")
@Slf4j
public class TagController {

    private final TagServiceImpl tagService;


    public TagController(TagServiceImpl tagService) {
        this.tagService = tagService;
    }

    @ApiOperation(value = "获取标签列表", notes = "获取标签列表")
    @GetMapping("tags")
    public Response<List<TagResponse>> tags() {
        TagQuery tagQuery = new TagQuery();
        tagQuery.setPageNumber(1);
        tagQuery.setPageSize(10);
        Page<TagResponse> page = tagService.tags(tagQuery);
        return ResponseUtils.success(page.getContent());
    }
}
