package com.songyuankun.wechat.secutity;

import com.songyuankun.wechat.entity.User;
import com.songyuankun.wechat.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author songyuankun
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;

    JWTAuthenticationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (StringUtils.isEmpty(header)) {
            header = request.getParameter("token");
        }
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(header);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);

    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        if (token != null) {
            // parse the token.
            String user = Jwts.parser()
                    .setSigningKey("MyJwtSecret")
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody()
                    .getSubject();

            if (user != null) {
                User one = userRepository.getOne(Integer.parseInt(user));
                List<String> roleList = Arrays.asList(one.getUserRole().split(","));
                List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
                roleList.forEach(role -> simpleGrantedAuthorities.add(new SimpleGrantedAuthority(role)));
                return new UsernamePasswordAuthenticationToken(user, "123456", simpleGrantedAuthorities);
            }
            return null;
        }
        return null;
    }
}