package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.entity.TagLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author songyuankun
 */
public interface TagLinkRepository extends JpaRepository<TagLink, Integer>, JpaSpecificationExecutor<TagLink> {


    List<TagLink> findAllByArticleId(Integer articleId);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    void deleteAllByArticleId(Integer articleId);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    void deleteAllByTagId(Integer id);
}
