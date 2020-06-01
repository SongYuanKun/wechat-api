package com.songyuankun.wechat.request.query;


import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author songyuankun
 */
@ApiModel
@Getter
@Setter
@ToString
public class ArticleQuery extends BasePageQuery {
    private String title;
    private String mediaId;
}
