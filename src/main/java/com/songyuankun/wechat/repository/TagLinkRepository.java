package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.entity.TagLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author songyuankun
 */
public interface TagLinkRepository extends JpaRepository<TagLink, Integer>, JpaSpecificationExecutor<TagLink> {


    List<TagLink> findAllByArticleId(Integer articleId);
}
