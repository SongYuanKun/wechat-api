package com.songyuankun.wechat.request.update;

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
public class CourseUpdateDetail implements Serializable {
    private int id;
    private String teacherName;
    private String introduction;

}
