package com.songyuankun.wechat.response;

import com.songyuankun.wechat.infrastructure.dataaccess.ArticlePO;
import com.songyuankun.wechat.entity.Tag;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author songyuankun
 */
@Getter
@Setter
@ToString
public class ArticlePOInfoResponse extends ArticlePO {

    private String categoryListStr;

    private List<Tag> tagList;
}
