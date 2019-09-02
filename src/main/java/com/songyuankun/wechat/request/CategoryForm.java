package com.songyuankun.wechat.request;

import com.songyuankun.wechat.entity.Category;
import io.swagger.annotations.ApiModel;
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
public class CategoryForm extends Category {
}
