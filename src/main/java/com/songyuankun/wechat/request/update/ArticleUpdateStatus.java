package com.songyuankun.wechat.request.update;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author songyuankun
 */
@Getter
@Setter
@ToString
public class ArticleUpdateStatus {
    private Integer id;
    private Boolean recommend;
    private Boolean top;
    private Boolean publish;
}
