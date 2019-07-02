package com.songyuankun.wechat.controller;

import com.songyuankun.wechat.dao.Course;
import com.songyuankun.wechat.repository.CourseRepository;
import com.songyuankun.wechat.request.CourseForm;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author songyuankun
 */
@Api
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
    public Course save(@RequestBody CourseForm courseForm) {
        Course course = new Course();
        BeanUtils.copyProperties(courseForm, course);
        course.setStatus(0);
        courseRepository.save(course);
        return course;
    }

    @PostMapping("public/all")
    public List<Course> all() {
        return courseRepository.findAll();
    }

    @PostMapping("public/page")
    public Page<Course> page(Authentication authentication, @RequestParam(required = false, defaultValue = "0") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        String name = authentication.getName();
        log.info(name);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return courseRepository.findAll(pageable);
    }

}
