package com.songyuankun.wechat.blog.application.query;

import lombok.Data;

@Data
public class FindArticleListQuery extends  PageListQuery {
    private Boolean favorite;
    private Boolean latest;
    private Boolean recommend;

}
