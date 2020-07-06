package com.songyuankun.wechat.common;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author songyuankun
 */
@Component
@Slf4j
public class TokenCommon {

    private AuthenticationManager authenticationManager;

    public TokenCommon(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public String getToken(String openid, String env, List<SimpleGrantedAuthority> simpleGrantedAuthorities) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(openid, "123456", simpleGrantedAuthorities);
        usernamePasswordAuthenticationToken.setDetails(env);
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        String token = Jwts.builder()
                .setSubject(((org.springframework.security.core.userdetails.User) authenticate.getPrincipal()).getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 24 * 1000))
                .signWith(SignatureAlgorithm.HS512, "MyJwtSecret")
                .compact();
        log.info("Bearer " + token);
        return token;
    }
}
