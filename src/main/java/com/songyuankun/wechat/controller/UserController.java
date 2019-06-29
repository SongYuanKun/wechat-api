package com.songyuankun.wechat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author songyuankun
 */
@RestController
@Slf4j
public class UserController {

    @GetMapping("/user")
    public String user(@AuthenticationPrincipal Principal principal) {
        log.info(principal.getName());
        return principal.getName();
    }

}
