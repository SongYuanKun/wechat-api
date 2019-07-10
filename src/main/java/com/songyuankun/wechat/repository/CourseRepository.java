package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.dao.Course;
import com.songyuankun.wechat.request.update.CourseUpdateDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author songyuankun
 */
public interface CourseRepository extends JpaRepository<Course, Integer>, JpaSpecificationExecutor<Course> {

    @Modifying
    @Query(value = "update Course c set c.teacherName= :#{#course.teacherName} ,c.introduction= :#{#course.introduction}  where c.id= :#{#course.id}")
    Integer updateDetail(Course course);
}
