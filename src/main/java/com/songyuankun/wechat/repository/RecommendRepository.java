package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.entity.Recommend;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author songyuankun
 */
public interface RecommendRepository extends JpaRepository<Recommend, Integer>, JpaSpecificationExecutor<Recommend> {

    @Modifying
    @Query(value = "update Recommend set   top=:b where id <> :id")
    void updateTopByNotEqualId(@Param("b") boolean b, @Param("id") Integer id);
}
