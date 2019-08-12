package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.entity.Classification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author songyuankun
 */
public interface ClassificationRepository extends JpaRepository<Classification, Integer>, JpaSpecificationExecutor<Classification> {

}
