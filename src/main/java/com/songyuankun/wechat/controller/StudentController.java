package com.songyuankun.wechat.controller;

import com.songyuankun.wechat.dao.CourseEnroll;
import com.songyuankun.wechat.dao.User;
import com.songyuankun.wechat.repository.CourseEnrollRepository;
import com.songyuankun.wechat.repository.CourseRepository;
import com.songyuankun.wechat.repository.UserRepository;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("student")
@Slf4j
public class StudentController {
    private final CourseEnrollRepository courseEnrollRepository;
    private final UserRepository userRepository;

    @Autowired
    public StudentController(CourseEnrollRepository courseEnrollRepository, UserRepository userRepository) {
        this.courseEnrollRepository = courseEnrollRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("getAllStudents")
    public List<User> page(@RequestParam(value = "courseId") Integer courseId) {
        CourseEnroll courseEnroll = new CourseEnroll();
        courseEnroll.setCourseId(courseId);
        List<CourseEnroll> all = courseEnrollRepository.findAll(Example.of(courseEnroll));
        return all.stream().map(courseEnroll1 -> userRepository.getOne(courseEnroll1.getUserId())).collect(Collectors.toList());
    }

}
