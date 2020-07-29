package com.songyuankun.wechat.repository;

import com.songyuankun.wechat.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author songyuankun
 */
public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {

    /**
     * @param parentId parentId
     * @return List<Category>
     */
    List<Category> findAllByParentId(int parentId);
}
