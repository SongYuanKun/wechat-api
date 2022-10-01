package com.songyuankun.wechat.request.update;

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
public class CourseUpdateDetail implements Serializable {
    private int id;
    private String teacherName;
    private String introduction;

}
