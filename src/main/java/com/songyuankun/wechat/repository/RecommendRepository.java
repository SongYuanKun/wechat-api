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

    /**
     * 将其他置顶取消
     *
     * @param id id
     */
    @Modifying
    @Transactional
    @Query(value = "update Recommend set   top=false where id <> :id")
    void updateTopFalseByNotEqualId(@Param("id") Integer id);
}
