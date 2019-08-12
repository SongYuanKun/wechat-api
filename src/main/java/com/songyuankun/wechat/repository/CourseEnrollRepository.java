package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.entity.CourseEnroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author songyuankun
 */
public interface CourseEnrollRepository extends JpaRepository<CourseEnroll, Integer>, JpaSpecificationExecutor<CourseEnroll> {

}
