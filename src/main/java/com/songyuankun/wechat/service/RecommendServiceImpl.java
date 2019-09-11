package com.songyuankun.wechat.service;


import com.songyuankun.wechat.entity.Recommend;
import com.songyuankun.wechat.repository.RecommendRepository;
import com.songyuankun.wechat.request.query.RecommendQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author songyuankun
 */
@Service
public class RecommendServiceImpl {
    private RecommendRepository recommendRepository;

    public RecommendServiceImpl(RecommendRepository recommendRepository) {
        this.recommendRepository = recommendRepository;
    }

    public Page<Recommend> findAll(RecommendQuery recommendQuery) {
        Pageable pageable = PageRequest.of(recommendQuery.getPageNumber() - 1, recommendQuery.getPageSize());
        return recommendRepository.findAll(pageable);
    }


    public Recommend getOne(Integer id) {
        return recommendRepository.getOne(id);
    }

    public Recommend save(Recommend recommend) {
        return recommendRepository.save(recommend);
    }

    public void updateTop(Integer id) {
        // 更新本条
        Recommend recommend = recommendRepository.getOne(id);
        recommend.setTop(true);
        recommendRepository.save(recommend);
        //批量更新其他
        recommendRepository.updateTopByNotEqualId(false, id);
    }

    public void removeByIds(List<Integer> asList) {
        asList.forEach(id -> recommendRepository.deleteById(id));
    }

    public void removeById(Integer id) {
        recommendRepository.deleteById(id);
    }

    public Page<Recommend> findAllOrderByTop() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.desc("id"), Sort.Order.asc("top")));
        return recommendRepository.findAll(pageable);
    }
}
