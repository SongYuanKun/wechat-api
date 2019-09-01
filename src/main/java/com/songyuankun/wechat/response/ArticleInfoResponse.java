package com.songyuankun.wechat.response;

import com.songyuankun.wechat.entity.Article;
import com.songyuankun.wechat.entity.Tag;
import io.swagger.annotations.ApiModel;
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
public class ArticleInfoResponse extends Article {

    private String categoryListStr;

    private List<Tag> tagList;
}
