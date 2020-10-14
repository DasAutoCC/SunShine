package com.lkz.blog.web.config;

import com.lkz.blog.web.webService.impl.RedisRememberMeServiceImpl;
import com.lkz.blog.web.webService.impl.BlogUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private BlogUserDetailsService userDetailsService;

    @Resource
    private RedisRememberMeServiceImpl rememberMeServiceAndLogoutHandler;

    @Resource
    private BlogConfigProperties blogConfigProperties;

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        PasswordEncoder passwordEncoder = NoOpPasswordEncoder.getInstance();
        return passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/blog/addArticle",
                        "/upload-image",
                        "/getUncheckedComment",
                        "/doCheckComment",
                        "/upd-article",
                        "/blog/upd-article")
                .hasAuthority("管理员")
                .antMatchers("/insComment")
                .authenticated()
                .and()
                .userDetailsService(userDetailsService)
                .exceptionHandling(new SecurityExceptionHandler())
                .headers().frameOptions().sameOrigin()//允许页面在同源网站下嵌套
                .and()
                .exceptionHandling()
                .and()
                .formLogin()
                .failureHandler(new BlogAuthenticationFailureHandler())
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .permitAll()
                .and()
                .csrf()
                .disable();
        if (blogConfigProperties.isRedisRemember()){
            http.rememberMe()
                    .alwaysRemember(false)
                    .rememberMeServices(rememberMeServiceAndLogoutHandler).and().logout().addLogoutHandler(rememberMeServiceAndLogoutHandler);//分布式session实现
        }
        if (blogConfigProperties.isCustomizerLoginPageAndResp()){
            http.exceptionHandling().authenticationEntryPoint(new BlogAuthenticationEntryPoint());//配置需要登录的操作
        }
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers();
    }
}
