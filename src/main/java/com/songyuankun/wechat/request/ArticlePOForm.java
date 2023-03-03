package com.songyuankun.wechat.request;

import com.songyuankun.wechat.entity.ArticlePO;
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
public class ArticlePOForm extends ArticlePO {

    private List<Tag> tagList;

}