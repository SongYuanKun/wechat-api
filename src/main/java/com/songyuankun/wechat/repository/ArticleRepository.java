package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author songyuankun
 */
public interface ArticleRepository extends JpaRepository<Article, Integer>, JpaSpecificationExecutor<Article> {


    @Modifying
    @Query(value = "update article set like_num = like_num + 1 where id = :id")
    void updateLikeNum(@Param("id") int id);

    @Modifying
    @Query(value = "update article set read_num = read_num + 1 where id = :id")
    void updateReadNum(@Param("id") int id);

}
