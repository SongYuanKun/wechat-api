package com.songyuankun.wechat.controller.publicapi;

import com.songyuankun.wechat.entity.Course;
import com.songyuankun.wechat.repository.CourseRepository;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("public/course")
@Slf4j
public class CoursePublicController {
    private final CourseRepository courseRepository;

    @Autowired
    public CoursePublicController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    @GetMapping("getById")
    public Course getById(@RequestParam Integer id) {
        return courseRepository.getOne(id);
    }

    @PostMapping("all")
    public List<Course> all() {
        return courseRepository.findAll();
    }

    @PostMapping("page")
    public Page<Course> page(@RequestParam(required = false, defaultValue = "1") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return courseRepository.findAll(pageable);
    }

}
