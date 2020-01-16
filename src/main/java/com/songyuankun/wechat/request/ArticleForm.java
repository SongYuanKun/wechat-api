package com.songyuankun.wechat.request;

import com.songyuankun.wechat.entity.Article;
import com.songyuankun.wechat.entity.Tag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author songyuankun
 */

@ApiModel
@Getter
@Setter
@ToString
public class ArticleForm extends Article {

    @ApiModelProperty("标签列表")
    private List<Tag> tagList;

}