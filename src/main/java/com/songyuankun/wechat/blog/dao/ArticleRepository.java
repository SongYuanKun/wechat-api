package com.songyuankun.wechat.blog.dao;

import com.songyuankun.wechat.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author songyuankun
 */
public interface ArticleRepository extends JpaRepository<Article, Integer>, JpaSpecificationExecutor<Article> {


    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query(value = "update Article set likeNum =  likeNum + 1 where id = :id")
    void updateLikeNum(@Param("id") Integer id);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query(value = "update Article set readNum = readNum + 1 where id = :id")
    void updateReadNum(@Param("id") Integer id);

    Article queryFirstById(Integer id);

    List<Article> queryAllByIdIn(List<Integer> ids);

}
