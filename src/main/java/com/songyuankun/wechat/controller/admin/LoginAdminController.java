package com.songyuankun.wechat.controller.admin;

import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.common.TokenCommon;
import com.songyuankun.wechat.dao.User;
import com.songyuankun.wechat.repository.UserRepository;
import com.songyuankun.wechat.util.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author songyuankun
 */
@RestController
@Slf4j
@RequestMapping("admin")
public class LoginAdminController {

    private TokenCommon tokenCommon;

    private UserRepository userRepository;

    public LoginAdminController(TokenCommon tokenCommon, UserRepository userRepository) {
        this.tokenCommon = tokenCommon;
        this.userRepository = userRepository;
    }

    @PostMapping("loginByPassword")
    public Response<String> loginByPassword(@RequestParam String phone, @RequestParam String password) {
        String passwordMd5 = Md5Util.stringInMd5(password);
        User user = userRepository.findByPhoneAndPassword(phone, passwordMd5);
        if (user == null) {
            return ResponseUtils.error("没有该用户");
        }
        if (!user.getUserRole().contains("ROLE_ADMIN")) {
            return ResponseUtils.error("没有权限");
        }
        String openid = user.getUid();
        List<String> roleList = Arrays.asList(user.getUserRole().split(","));
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        roleList.forEach(role -> simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role)));

        String token = tokenCommon.getToken(openid, simpleGrantedAuthorities);
        return ResponseUtils.success("Bearer " + token);
    }
}
