package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.dao.RoomAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

/**
 * @author songyuankun
 */
public interface RoomAppointmentRepository extends JpaRepository<RoomAppointment, Integer>, JpaSpecificationExecutor<RoomAppointment> {


    List<RoomAppointment> queryAllByStartTimeBetween(Date startTime1, Date startTime2);

    List<RoomAppointment> queryAllByEndTimeBetween(Date startTime1, Date startTime2);

    List<RoomAppointment> queryAllByDay(String day);

}
