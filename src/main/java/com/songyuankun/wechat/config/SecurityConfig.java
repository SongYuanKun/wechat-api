package com.songyuankun.wechat.config;

import com.songyuankun.wechat.secutity.DbUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author songyuankun
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private DbUserDetailsServiceImpl dbUserDetailsService;


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void setAnyUserDetailsService(DbUserDetailsServiceImpl dbUserDetailsService) {
        this.dbUserDetailsService = dbUserDetailsService;
    }

    /**
     * 添加 UserDetailsService， 实现自定义登录校验
     */
    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(dbUserDetailsService);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //登记界面，默认是permit All
        http.formLogin()
                .and()
                //不用身份认证可以访问
                .authorizeRequests().antMatchers("/", "/login").permitAll()
                .and()
                //其它的请求要求必须有身份认证
                .authorizeRequests().anyRequest().authenticated()
                .and()
                //防止CSRF（跨站请求伪造）配置
                .csrf()
                .disable();
    }


}