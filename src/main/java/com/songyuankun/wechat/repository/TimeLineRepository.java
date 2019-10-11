package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author songyuankun
 */
public interface TimeLineRepository extends JpaRepository<Article, Integer> {

    @Query(value = " select DATE_FORMAT(create_time,'%Y') as year,count(DATE_FORMAT(create_time,'%Y')) as count from article where publish = 1 group by DATE_FORMAT(create_time,'%Y')", nativeQuery = true)
    List<Object[]> listTimeline();

    @Query(value = "select id,title,description,create_time  from article where DATE_FORMAT(create_time,'%Y')=:year and DATE_FORMAT(create_time,'%m')=:month and publish = 1", nativeQuery = true)
    List<Object[]> listTimelinePost(@Param("year") Integer year, @Param("month") Integer month);
}
