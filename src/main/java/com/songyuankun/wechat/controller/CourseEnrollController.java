package com.songyuankun.wechat.controller;

import com.songyuankun.wechat.dao.CourseEnroll;
import com.songyuankun.wechat.repository.CourseEnrollRepository;
import com.songyuankun.wechat.repository.CourseRepository;
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

import java.util.Date;
import java.util.List;

/**
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("course_enroll")
@Slf4j
public class CourseEnrollController {
    private final CourseEnrollRepository courseEnrollRepository;
    private final CourseRepository courseRepository;

    public CourseEnrollController(CourseEnrollRepository courseEnrollRepository, CourseRepository courseRepository) {
        this.courseEnrollRepository = courseEnrollRepository;
        this.courseRepository = courseRepository;
    }


    @PostMapping("save")
    public CourseEnroll save(Authentication authentication, @RequestBody CourseEnrollForm courseEnrollForm) {
        CourseEnroll courseEnroll = new CourseEnroll();
        BeanUtils.copyProperties(courseEnrollForm, courseEnroll);
        int userId = Integer.parseInt(authentication.getName());
        courseEnroll.setUserId(userId);
        courseEnroll.setStatus(0);
        courseEnroll.setCreateTime(new Date());
        courseEnrollRepository.save(courseEnroll);
        return courseEnroll;
    }

    @PostMapping("my/enroll")
    public Page<CourseEnroll> page(Authentication authentication, @RequestParam(required = false, defaultValue = "0") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Integer userId = Integer.valueOf(authentication.getName());
        CourseEnroll courseEnroll = new CourseEnroll();
        courseEnroll.setUserId(userId);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<CourseEnroll> courseEnrollPage = courseEnrollRepository.findAll(Example.of(courseEnroll), pageable);
        List<CourseEnroll> content = courseEnrollPage.getContent();
        content.forEach(c -> c.setCourse(courseRepository.getOne(c.getCourseId())));
        return courseEnrollPage;
    }


}
