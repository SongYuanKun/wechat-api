package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * @author songyuankun
 */
public interface ArticleRepository extends JpaRepository<Article, Integer>, JpaSpecificationExecutor<Article> {


    @Modifying
    @javax.transaction.Transactional(rollbackOn = Exception.class)
    @Query(value = "update Article set like_num = like_num + 1 where id = :id")
    void updateLikeNum(@Param("id") Integer id);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query(value = "update Article set read_num = read_num + 1 where id = :id")
    void updateReadNum(@Param("id") Integer id);

}
