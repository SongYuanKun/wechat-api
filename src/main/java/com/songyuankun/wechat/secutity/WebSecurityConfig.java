package com.songyuankun.wechat.secutity;

import com.songyuankun.wechat.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author songyuankun
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserRepository userRepository;
    private final DbUserDetailsServiceImpl userDetailsService;

    public WebSecurityConfig(UserRepository userRepository, DbUserDetailsServiceImpl userDetailsService) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().authorizeRequests()
                .antMatchers("/blog/**","/weixin/**", "/public/**", "/login", "/admin/captcha.jpg", "/admin/loginByPassword").permitAll()
                .antMatchers("/",
                        "/favicon.ico",
                        "/**/*.png",
                        "/**/*.gif",
                        "/**/*.svg",
                        "/**/*.jpg",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js").permitAll()
                .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                .antMatchers("/api/**").hasAuthority("ROLE_USER")
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), userRepository));
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui", "/swagger-resources", "/swagger-resources/**", "/configuration/security", "/swagger-ui.html", "/webjars/**");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}