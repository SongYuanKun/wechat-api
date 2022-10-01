package com.songyuankun.wechat.request.query;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author songyuankun
 */
@Getter
@Setter
@ToString
public class CategoryQuery extends BasePageQuery {
    private String name;
    private Integer type;
}
