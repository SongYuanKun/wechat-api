package com.songyuankun.wechat.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author songyuankun
 */
@Getter
@Setter
@ToString
public class CourseForm implements Serializable {
    private String name;
    private String img;
    private String teacherName;
    private String introduction;

}
