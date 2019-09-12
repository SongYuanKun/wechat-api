package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author songyuankun
 */
public interface RecommendRepository extends JpaRepository<Recommend, Integer>, JpaSpecificationExecutor<Recommend> {

    @Modifying
    @Transactional
    @Query(value = "update Recommend set   top=false where id <> :id")
    void updateTopByNotEqualId(@Param("id") Integer id);
}
