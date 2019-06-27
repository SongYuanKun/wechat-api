package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.dao.RoomAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author songyuankun
 */
public interface RoomAppointmentRepository extends JpaRepository<RoomAppointment, Integer>, JpaSpecificationExecutor<RoomAppointment> {

}
