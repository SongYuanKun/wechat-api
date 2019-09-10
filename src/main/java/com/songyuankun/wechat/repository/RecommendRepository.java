package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author songyuankun
 */
public interface RecommendRepository extends JpaRepository<Recommend, Integer>, JpaSpecificationExecutor<Recommend> {

    @Modifying
    @Query(value = "update Recommend set   top=false where id <> :id")
    void updateTopByNotEqualId(boolean b, @Param("id") Integer id);
}
