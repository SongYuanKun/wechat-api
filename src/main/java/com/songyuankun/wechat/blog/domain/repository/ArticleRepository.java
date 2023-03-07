package com.songyuankun.wechat.blog.domain.repository;

import com.songyuankun.wechat.infrastructure.dataaccess.ArticlePO;
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
public interface ArticleRepository extends JpaRepository<ArticlePO, Integer>, JpaSpecificationExecutor<ArticlePO> {


    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query(value = "update ArticlePO set likeNum =  likeNum + 1 where id = :id")
    void updateLikeNum(@Param("id") Integer id);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query(value = "update ArticlePO set readNum = readNum + 1 where id = :id")
    void updateReadNum(@Param("id") Integer id);

    ArticlePO queryFirstById(Integer id);

    List<ArticlePO> queryAllByIdIn(List<Integer> ids);

}
