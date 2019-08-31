package com.songyuankun.wechat.response;

import com.songyuankun.wechat.entity.Category;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author songyuankun
 */
@ApiModel
@Getter
@Setter
@ToString
public class CategoryPageResponse {

    private Page<Category> categoryPage;

    private List<Category> childrenList;


}
