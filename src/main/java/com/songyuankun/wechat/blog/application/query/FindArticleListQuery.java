package com.songyuankun.wechat.blog.application.query;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FindArticleListQuery extends PageListQuery {
    private Boolean favorite;
    private Boolean latest;
    private Boolean recommend;

}
