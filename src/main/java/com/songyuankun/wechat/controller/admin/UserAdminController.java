package com.songyuankun.wechat.controller.admin;

import com.songyuankun.wechat.common.DaoCommon;
import com.songyuankun.wechat.entity.User;
import com.songyuankun.wechat.repository.UserRepository;
import com.songyuankun.wechat.request.UserForm;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;

/**
 * @author songyuankun
 */
@Api(value = "admin/user")
@RestController
@Slf4j
@RequestMapping("admin/user")
public class UserAdminController {


    private UserRepository userRepository;

    public UserAdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public User user(@AuthenticationPrincipal Principal principal) {
        String name = principal.getName();
        return userRepository.getOne(Integer.parseInt(name));
    }

    @PostMapping("saveOrUpdate")
    @Transactional(rollbackOn = Exception.class)
    public User saveOrUpdate(Authentication authentication, @RequestBody UserForm userForm) {
        User user;
        if (userForm.getId() != null) {
            user = userRepository.getOne(userForm.getId());
            DaoCommon.updateDao(authentication, user);

        } else {
            user = new User();
            DaoCommon.createDao(authentication, user);
        }
        BeanUtils.copyProperties(userForm, user);
        return userRepository.save(user);
    }


    @PostMapping("page")
    public Page<User> page(@RequestParam(required = false, defaultValue = "1") Integer pageNumber, @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return userRepository.findAll(pageable);
    }

}
