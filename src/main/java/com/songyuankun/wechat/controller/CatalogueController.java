package com.songyuankun.wechat.controller;

import com.songyuankun.wechat.dao.Catalogue;
import com.songyuankun.wechat.repository.CatalogueRepository;
import com.songyuankun.wechat.request.CatalogueForm;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author songyuankun
 */
@Api
@RestController
@RequestMapping("catalogue")
@Slf4j
public class CatalogueController {
    private final CatalogueRepository catalogueRepository;

    @Autowired
    public CatalogueController(CatalogueRepository catalogueRepository) {
        this.catalogueRepository = catalogueRepository;
    }

    @PostMapping("saveOrUpdate")
    public Catalogue save(Authentication authentication, @RequestBody CatalogueForm catalogueForm) {
        Integer userId = Integer.valueOf(authentication.getName());
        Catalogue catalogue = new Catalogue();
        BeanUtils.copyProperties(catalogueForm, catalogue);
        if (catalogue.getId()==null){
            catalogue.setStatus(0);
            catalogue.setUserId(userId);
        }
        catalogueRepository.save(catalogue);
        return catalogue;
    }

    @PostMapping("update")
    @Transactional(rollbackOn = Exception.class)
    public Catalogue update(@RequestBody CatalogueForm catalogueForm) {
        Catalogue catalogue = new Catalogue();
        BeanUtils.copyProperties(catalogueForm, catalogue);
        return catalogueRepository.save(catalogue);
    }

    @GetMapping("public/getById")
    public Catalogue getById(@RequestParam Integer id) {
        return catalogueRepository.getOne(id);
    }

    @PostMapping("public/all")
    public List<Catalogue> publicAll() {
        Catalogue catalogue = new Catalogue();
        catalogue.setStatus(1);
        return catalogueRepository.findAll(Example.of(catalogue));
    }

    @PostMapping("all")
    public List<Catalogue> all() {
        return catalogueRepository.findAll();
    }

    @PostMapping("page")
    public Page<Catalogue> page(@RequestParam(required = false, defaultValue = "0") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return catalogueRepository.findAll(pageable);
    }

    @PostMapping("public/page")
    public Page<Catalogue> publicPage(@RequestParam(required = false, defaultValue = "0") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Catalogue catalogue = new Catalogue();
        catalogue.setStatus(1);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return catalogueRepository.findAll(Example.of(catalogue), pageable);
    }

    @PostMapping("my/create")
    public Page<Catalogue> page(Authentication authentication, @RequestParam(required = false, defaultValue = "0") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Integer userId = Integer.valueOf(authentication.getName());
        Catalogue catalogue = new Catalogue();
        catalogue.setUserId(userId);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return catalogueRepository.findAll(Example.of(catalogue), pageable);
    }
}
