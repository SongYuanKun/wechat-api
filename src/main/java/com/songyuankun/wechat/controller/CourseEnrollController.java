package com.songyuankun.wechat.controller;

import com.songyuankun.wechat.dao.CourseEnroll;
import com.songyuankun.wechat.repository.CourseEnrollRepository;
import com.songyuankun.wechat.request.CourseEnrollForm;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("course_enroll")
@Slf4j
public class CourseEnrollController {
    private final CourseEnrollRepository courseEnrollRepository;

    public CourseEnrollController(CourseEnrollRepository courseEnrollRepository) {
        this.courseEnrollRepository = courseEnrollRepository;
    }


    @PostMapping("save")
    public CourseEnroll save(Authentication authentication, @RequestBody CourseEnrollForm courseEnrollForm) {
        CourseEnroll courseEnroll = new CourseEnroll();
        log.info(authentication.getName());
        BeanUtils.copyProperties(courseEnrollForm, courseEnroll);
        courseEnroll.setStatus(0);
        courseEnrollRepository.save(courseEnroll);
        return courseEnroll;
    }

    @PostMapping("page")
    public Page<CourseEnroll> page(Authentication authentication, @RequestParam(required = false, defaultValue = "0") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Integer name = Integer.valueOf(authentication.getName());
        CourseEnroll courseEnroll = new CourseEnroll();
        courseEnroll.setId(name);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return courseEnrollRepository.findAll(Example.of(courseEnroll), pageable);
    }

}
