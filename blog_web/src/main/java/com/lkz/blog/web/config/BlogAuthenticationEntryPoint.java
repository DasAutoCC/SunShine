package com.lkz.blog.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkz.blog.web.common.RespPojo;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 这个EntryPoint引用官方的话来说就是用来向用户请求凭证的，在用户访问受保护的资源的时候，springSecurity会抛出AuthenticationException
 * 然后EntryPoint会捕获这个异常，让后返回客户端302状态码，让客户端重定向到登陆页面进行登录，登陆成功后返回原界面，这里直接自定义，不需要重定向
 */
public class BlogAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse resp, AuthenticationException authException) throws IOException, ServletException {
        String message = "您需要登陆才能进行下一步操作";
        RespPojo exception = RespPojo.exception(message);
        ObjectMapper ob = new ObjectMapper();
        String s = ob.writeValueAsString(exception);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().write(s);
        resp.getWriter().flush();
        resp.getWriter().close();
        return;
    }
}
