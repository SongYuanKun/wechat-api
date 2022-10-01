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
public class CourseEnrollForm implements Serializable {
    private int courseId;
    private int userId;
    private String userName;
    private String phone;

}
