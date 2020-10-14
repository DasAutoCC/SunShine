package com.lkz.blog.web.webService.impl;

import com.lkz.blog.pojo.User;
import com.lkz.blog.web.utils.CookieUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 盛世繁华
 * 分布式session实现,用户如果选中rememberme，前端传参数为remember-me:on,后端会自动调用remembermeService
 */
@Component
public class RedisRememberMeServiceImpl implements RememberMeServices, LogoutHandler {

    Logger logger = LoggerFactory.getLogger(RedisRememberMeServiceImpl.class);

    public static final String SPRING_SECURITY_CONTEXT_KEY = "SPRING_SECURITY_CONTEXT";
    private String springSecurityContextKey = SPRING_SECURITY_CONTEXT_KEY;
    private static final String COOKIE_NAME = "authentication_id";
    private String cookieDomain;

    @Resource
    private RedisTemplate<String,User> redisTemplate;

    @Override
    public Authentication autoLogin(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        Cookie theCookie = CookieUtil.findTheCookie(cookies, COOKIE_NAME);
        if (theCookie == null){
            return null;
        }
        String rememberCookie = theCookie.getValue();
        if (rememberCookie.length() == 0) {
            logger.debug("Cookie是空的，此cookie将被清除");
            logger.info("Cookie是空的，此cookie将被清除");
            if (theCookie.getDomain()!=null){
                cookieDomain = theCookie.getDomain();
            }
            cancelCookie(request, response);
            return null;
        }
        User principal = redisTemplate.boundValueOps(rememberCookie).get();
        if (principal == null){
            logger.debug("当前认证信息已失效，或cookie非法");
            cancelCookie(request,response);
            return null;
        }
        //获取验证主体
        //创建一个新的token，让userDetailService去验证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(principal,principal.getPassword());
        return usernamePasswordAuthenticationToken;
    }


    private String getCookiePath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        return contextPath.length() > 0 ? contextPath : "/";
    }

    protected void cancelCookie(HttpServletRequest request, HttpServletResponse response) {
        logger.debug("Cancelling cookie");
        Cookie cookie = new Cookie(COOKIE_NAME, null);
        cookie.setMaxAge(0);
        cookie.setPath(getCookiePath(request));
        if (cookieDomain != null) {
            cookie.setDomain(cookieDomain);
        }
        response.addCookie(cookie);
    }

    @Override
    public void loginFail(HttpServletRequest request, HttpServletResponse response) {
        cancelCookie(request, response);
    }

    /**
     * 用户登录成功的时候会调用这个方法，
     * 官方示意图<a>https://docs.spring.io/spring-security/site/docs/5.4.0-RC1/reference/html5/#servlet-authentication-form <a/>
     * 登陆成功后将session存储到redis中
     * @param request
     * @param response
     * @param successfulAuthentication
     */
    @Override
    public void loginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        String parameter = request.getParameter("remember-me");
        //如果不使用记住我登录则直接结束语句
        if (parameter == null || !parameter.equals("on")){
            return;
        }
        String[] split = UUID.randomUUID().toString().split("-");
        StringBuilder co = new StringBuilder();
        //获取cookie
        for (String s:split) {
            co.append(s);
        }
        //将cookie放入redis和响应中
        String rememberCookieKey = co.toString();

        User principal= (User)successfulAuthentication.getPrincipal();
        redisTemplate.boundValueOps(rememberCookieKey).set(principal);
        redisTemplate.expire(rememberCookieKey,60*60*24*3,TimeUnit.SECONDS);
        Cookie remMe= new Cookie(COOKIE_NAME,rememberCookieKey);
        remMe.setMaxAge(60*60*24*3);
        response.addCookie(remMe);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Cookie theCookie = CookieUtil.findTheCookie(request.getCookies(), COOKIE_NAME);
        if (theCookie == null){
            return;
        }
        String rememberCookieKey = theCookie.getValue();
        //清除session缓存
        Boolean delete = redisTemplate.delete(rememberCookieKey);
        //清除本地session
        HttpSession session = request.getSession(false);
        Object attribute = session == null ? null : session.getAttribute(springSecurityContextKey);
        if (attribute == null){
            logger.debug("当前会话session内容中的认证信息已被清除");
            //do nothing
        }else {
            logger.debug("将清除当前会话session中的认证信息。。。");
            session.removeAttribute(springSecurityContextKey);
        }
        if (!delete){
            logger.debug("redis缓存已清除");
            return;
        }
        //清除cookie
        cancelCookie(request, response);
    }
}
