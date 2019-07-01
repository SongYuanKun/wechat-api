package com.songyuankun.wechat.secutity;

import com.alibaba.fastjson.JSONObject;
import com.songyuankun.wechat.common.WeChatCommon;
import com.songyuankun.wechat.dao.User;
import com.songyuankun.wechat.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 验证用户名密码正确后，生成一个token，并将token返回给客户端
 * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法
 * attemptAuthentication ：接收并解析用户凭证。
 * successfulAuthentication ：用户成功登录后，这个方法会被调用，我们在这个方法里生成token。
 *
 * @author zhaoxinguo on 2017/9/12.
 */
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private  WeChatCommon weChatCommon;


    private UserRepository userRepository;


    public JWTLoginFilter(AuthenticationManager authenticationManager, WeChatCommon weChatCommon, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.weChatCommon = weChatCommon;
        this.userRepository = userRepository;
    }


    /**
     * 接收并解析用户凭证
     *
     * @param req req
     * @param res res
     * @return Authentication
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) {

        req.getParameterMap();
        List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
        simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(byUuidKey.getUserName(), "123456", simpleGrantedAuthorities));
    }

    /**
     * 用户成功登录后，这个方法会被调用，我们在这个方法里生成token
     *
     * @param req   req
     * @param res   res
     * @param chain chain
     * @param auth  auth
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) {

        String token = Jwts.builder()
                .setSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, "MyJwtSecret")
                .compact();
        res.addHeader("Authorization", "Bearer " + token);
        logger.info(token);
    }
}