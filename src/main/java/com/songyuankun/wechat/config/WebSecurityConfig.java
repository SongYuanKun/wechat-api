package com.songyuankun.wechat.config;

import com.songyuankun.wechat.filter.CustomJsonLoginFilter;
import com.songyuankun.wechat.repository.UserRepository;
import com.songyuankun.wechat.secutity.DbUserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author songyauankun
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    private final DbUserDetailsServiceImpl dbUserDetailsService;

    public WebSecurityConfig(UserRepository userRepository, DbUserDetailsServiceImpl dbUserDetailsService) {
        this.userRepository = userRepository;
        this.dbUserDetailsService = dbUserDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return super.authenticationManager();
    }

    /**
     * 匹配 "/" 路径，不需要权限即可访问
     * 匹配 "/user" 及其以下所有路径，都需要 "USER" 权限
     * 登录地址为 "/login"，登录成功默认跳转到页面 "/user"
     * 退出登录的地址为 "/logout"，退出成功后跳转到页面 "/login"
     * 默认启用 CSRF
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/course/**").hasAuthority("USER");
        http.addFilterAt(customJsonLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * 添加 UserDetailsService， 实现自定义登录校验
     */
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(dbUserDetailsService);
    }

    /**
     * 自定义认证过滤器
     */
    private CustomJsonLoginFilter customJsonLoginFilter() {
        CustomJsonLoginFilter jsonLoginFilter = new CustomJsonLoginFilter("/login", userRepository);
        jsonLoginFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailureHandler());
        jsonLoginFilter.setAuthenticationSuccessHandler(new CustomAuthenticationSuccessHandler());
        return jsonLoginFilter;
    }
}