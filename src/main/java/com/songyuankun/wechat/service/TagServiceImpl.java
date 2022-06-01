package com.songyuankun.wechat.service;



import com.songyuankun.wechat.entity.Tag;
import com.songyuankun.wechat.entity.TagLink;
import com.songyuankun.wechat.repository.TagLinkRepository;
import com.songyuankun.wechat.repository.TagRepository;
import com.songyuankun.wechat.request.query.TagQuery;
import com.songyuankun.wechat.response.TagResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author songyuankun
 */
@Service
public class TagServiceImpl {
    private final TagRepository tagRepository;
    private final TagLinkRepository tagLinkRepository;

    public TagServiceImpl(TagRepository tagRepository, TagLinkRepository tagLinkRepository) {
        this.tagRepository = tagRepository;
        this.tagLinkRepository = tagLinkRepository;
    }

    public Page<Tag> findAllByQuery(TagQuery tagQuery) {
        Pageable pageable = PageRequest.of(tagQuery.getPageNumber() - 1, tagQuery.getPageSize());

        return tagRepository.findAll((root, criteriaQuery, criteriaBuilder)
                -> {
            String name = tagQuery.getName();
            List<Predicate> list = new ArrayList<>();
            if (StringUtils.isNotEmpty(name)) {
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + name + "%"));
            }
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }, pageable);
    }

    public Page<TagResponse> tags(TagQuery tagQuery) {
        Page<Tag> allByQuery = findAllByQuery(tagQuery);
        List<TagResponse> tagResponseList = new ArrayList<>();

        allByQuery.getContent().forEach(tag -> {
            long linkNum = getTagLinkNum(tag.getId());
            TagResponse tagResponse = new TagResponse();
            BeanUtils.copyProperties(tag, tagResponse);
            tagResponse.setLinkNum(linkNum);
            tagResponseList.add(tagResponse);
        });
        return new PageImpl<>(tagResponseList, allByQuery.getPageable(), allByQuery.getTotalElements());


    }


    private long getTagLinkNum(Integer tagId) {
        TagLink tagLink = new TagLink();
        tagLink.setTagId(tagId);
        return tagLinkRepository.count(Example.of(tagLink));
    }


    public List<Tag> getTagsByArticleId(Integer articleId) {
        List<Integer> idList = tagLinkRepository.findAllByArticleId(articleId).stream().map(TagLink::getTagId).collect(Collectors.toList());
        if (idList.isEmpty()) {
            return new ArrayList<>();
        }
        return tagRepository.findAll(((root, criteriaQuery, criteriaBuilder) -> {
            CriteriaBuilder.In<Integer> in = criteriaBuilder.in(root.get("id").as(Integer.class));
            idList.forEach(in::value);
            List<Predicate> list = new ArrayList<>();
            list.add(in);
            Predicate[] p = new Predicate[list.size()];
            return criteriaBuilder.and(list.toArray(p));
        }));
    }

    public void saveTagAndNew(List<Tag> tagList, Integer id) {
        tagLinkRepository.deleteAllByArticleId(id);
        tagList.forEach(tag -> {
            if (tag.getId() == null) {
                tagRepository.save(tag);
            }
            TagLink tagLink = new TagLink(id, tag.getId());
            tagLinkRepository.save(tagLink);
        });
    }

    public Tag getOne(Integer id) {
        return tagRepository.getOne(id);
    }

    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    public List<Tag> findAll(Example<Tag> of) {
        return tagRepository.findAll(of);
    }

    public void deleteById(Integer id) {
        tagLinkRepository.deleteAllByTagId(id);
        tagRepository.deleteById(id);
    }
}
