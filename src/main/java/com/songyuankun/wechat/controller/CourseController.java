package com.songyuankun.wechat.controller;

import com.songyuankun.wechat.common.DaoCommon;
import com.songyuankun.wechat.entity.Course;
import com.songyuankun.wechat.repository.CourseRepository;
import com.songyuankun.wechat.request.CourseForm;
import com.songyuankun.wechat.request.update.CourseUpdateDetail;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

/**
 * @author songyuankun
 */
@Api(tags = "course")
@RestController
@RequestMapping("course")
@Slf4j
public class CourseController {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @PostMapping("save")
    public Course save(Authentication authentication, @RequestBody CourseForm courseForm) {
        Integer userId = Integer.valueOf(authentication.getName());
        Course course = new Course();
        BeanUtils.copyProperties(courseForm, course);
        course.setUserId(userId);
        DaoCommon.createDao(authentication, course);
        course.setStatus(0);
        courseRepository.save(course);
        return course;
    }

    @PostMapping("update_detail")
    @Transactional(rollbackOn = Exception.class)
    public Integer updateDetail(Authentication authentication, @RequestBody CourseUpdateDetail courseUpdateDetail) {
        Course course = new Course();
        DaoCommon.updateDao(authentication, course);
        BeanUtils.copyProperties(courseUpdateDetail, course);
        return courseRepository.updateDetail(course);
    }

    @PostMapping("my/create")
    public Page<Course> page(Authentication authentication, @RequestParam(required = false, defaultValue = "1") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Integer userId = Integer.valueOf(authentication.getName());
        Course course = new Course();
        course.setUserId(userId);
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return courseRepository.findAll(Example.of(course), pageable);
    }
}
