package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.entity.OssResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author songyuankun
 */
public interface OssResourceRepository extends JpaRepository<OssResource, Integer>, JpaSpecificationExecutor<OssResource> {

}
