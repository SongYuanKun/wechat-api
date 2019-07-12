package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.dao.TimePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author songyuankun
 */
public interface TimePointRepository extends JpaRepository<TimePoint, Integer>, JpaSpecificationExecutor<TimePoint> {

    List<TimePoint> findAllByIdAfter(Integer id);

}