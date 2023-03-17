package com.songyuankun.wechat.blog.application.query;

import lombok.Data;

@Data
public class PageListQuery {

    private Integer pageNumber;
    private Integer pageSize;
}
