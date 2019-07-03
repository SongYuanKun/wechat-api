package com.songyuankun.wechat.request;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author songyuankun
 */
@ApiModel
@Getter
@Setter
@ToString
public class CourseForm implements Serializable {
    private String name;
    private String description;
    private String img;
    private String teacherName;
    private String introduction;

}
