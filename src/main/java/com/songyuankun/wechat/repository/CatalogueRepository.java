package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.entity.Catalogue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author songyuankun
 */
public interface CatalogueRepository extends JpaRepository<Catalogue, Integer>, JpaSpecificationExecutor<Catalogue> {

}
