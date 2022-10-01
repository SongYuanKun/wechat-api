package com.songyuankun.wechat.response;

import com.songyuankun.wechat.entity.Tag;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author songyuankun
 */
@Getter
@Setter
@ToString
public class TagResponse extends Tag {


    private long linkNum;
}
