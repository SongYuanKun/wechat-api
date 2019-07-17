package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.dao.AppointmentTimePoint;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author songyuankun
 */
public interface AppointmentTimePointRepository extends JpaRepository<AppointmentTimePoint, Integer>, JpaSpecificationExecutor<AppointmentTimePoint> {


    List<AppointmentTimePoint> findAllByDay(String day);
    List<AppointmentTimePoint> findAllByUserId(Integer userId, Pageable pageable);

}
