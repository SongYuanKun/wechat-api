package com.songyuankun.wechat.response;

import com.songyuankun.wechat.entity.Tag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
public class TagResponse extends Tag {

    @ApiModelProperty(value = "链接数量")
    private long linkNum;
}
