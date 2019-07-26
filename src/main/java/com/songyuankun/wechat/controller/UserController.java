package com.songyuankun.wechat.controller;

import com.songyuankun.wechat.dao.User;
import com.songyuankun.wechat.repository.UserRepository;
import com.songyuankun.wechat.request.UserForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;

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
    public int updateUser(@AuthenticationPrincipal Principal principal, @RequestBody UserForm userForm) {
        int id = Integer.parseInt(principal.getName());
        return userRepository.updateUserNameAndPhone(id, userForm.getUserName(), userForm.getPhone());
    }




}
