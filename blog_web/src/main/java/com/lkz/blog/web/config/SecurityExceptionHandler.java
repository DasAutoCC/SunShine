package com.lkz.blog.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkz.blog.web.common.RespPojo;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SecurityExceptionHandler implements Customizer<ExceptionHandlingConfigurer<HttpSecurity>> {
    @Override
    public void customize(ExceptionHandlingConfigurer<HttpSecurity> httpSecurityExceptionHandlingConfigurer) {
        httpSecurityExceptionHandlingConfigurer.accessDeniedHandler(new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

                ObjectMapper ob = new ObjectMapper();
                RespPojo result = RespPojo.exception("权限不足");
                String s = ob.writeValueAsString(result);
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json; charset=utf-8");
                response.setStatus(403);
                PrintWriter writer = response.getWriter();
                writer.write(s);
                writer.flush();
                writer.close();
            }
        });

    }
}
