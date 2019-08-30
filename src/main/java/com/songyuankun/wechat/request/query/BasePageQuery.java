package com.songyuankun.wechat.request.query;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;


/**
 * @author songyuankun
 */
@Getter
@Setter
@ToString
public class BasePageQuery {
    @NotNull
    private Integer pageNumber;
    @NotNull
    private Integer pageSize;
}
