package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.dao.Course;
import com.songyuankun.wechat.dao.CourseEnroll;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @author songyuankun
 */
public interface CourseEnrollRepository extends JpaRepository<CourseEnroll, Integer>, JpaSpecificationExecutor<CourseEnroll> {

}
