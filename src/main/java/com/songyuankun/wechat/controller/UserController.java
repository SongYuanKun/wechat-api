package com.songyuankun.wechat.controller;

import com.songyuankun.wechat.common.DaoCommon;
import com.songyuankun.wechat.entity.User;
import com.songyuankun.wechat.repository.UserRepository;
import com.songyuankun.wechat.request.UserForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

/**
 * @author songyuankun
 */
@RestController
@Slf4j
@RequestMapping("user")
public class UserController {


    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("update")
    @Transactional(rollbackOn = Exception.class)
    public User updateUser(Authentication authentication, @RequestBody UserForm userForm) {
        int id = Integer.parseInt(authentication.getName());
        User one = userRepository.getOne(id);
        one.setPhone(userForm.getPhone());
        one.setUserName(userForm.getUserName());
        DaoCommon.createDao(authentication, one);
        return userRepository.save(one);
    }
}
