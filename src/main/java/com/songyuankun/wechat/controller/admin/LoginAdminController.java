package com.songyuankun.wechat.controller.admin;

import com.songyuankun.wechat.common.Response;
import com.songyuankun.wechat.common.ResponseUtils;
import com.songyuankun.wechat.common.TokenCommon;
import com.songyuankun.wechat.entity.User;
import com.songyuankun.wechat.repository.UserRepository;
import com.songyuankun.wechat.service.SysCaptchaServiceImpl;
import com.songyuankun.wechat.util.Md5Util;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author songyuankun
 */
@Api(value = "admin")
@RestController
@Slf4j
@RequestMapping("admin")
public class LoginAdminController {
    private SysCaptchaServiceImpl sysCaptchaService;
    private TokenCommon tokenCommon;

    private UserRepository userRepository;

    public LoginAdminController(SysCaptchaServiceImpl sysCaptchaService, TokenCommon tokenCommon, UserRepository userRepository) {
        this.sysCaptchaService = sysCaptchaService;
        this.tokenCommon = tokenCommon;
        this.userRepository = userRepository;
    }

    @PostMapping("loginByPassword")
    public Response<String> loginByPassword(@RequestParam String phone, @RequestParam String password, @RequestParam String uuid, @RequestParam String captcha, @RequestParam String env) {
        boolean validate = sysCaptchaService.validate(uuid, captcha);
        if (!validate) {
            return ResponseUtils.error("验证码错误");
        }
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

    @GetMapping("captcha.jpg")
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //获取图片验证码
        BufferedImage image = sysCaptchaService.getCaptcha(uuid);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }
}
