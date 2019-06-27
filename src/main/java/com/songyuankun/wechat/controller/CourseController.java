package com.songyuankun.wechat.controller;

import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.dao.Course;
import com.songyuankun.wechat.repository.CourseRepository;
import com.songyuankun.wechat.request.CourseForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author songyuankun
 */
@RestController
@RequestMapping("course")
@Slf4j
public class CourseController {
    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @PostMapping("save")
    public Response<Course> save(CourseForm course) {
        Response<Course> response = new Response<>();
        courseRepository.save(course);
        response.setData(course);
        return response;
    }

    @PostMapping("all")
    public Response<List<Course>> all() {
        Response<List<Course>> response = new Response<>();
        List<Course> all = courseRepository.findAll();
        response.setData(all);
        return response;
    }

}
