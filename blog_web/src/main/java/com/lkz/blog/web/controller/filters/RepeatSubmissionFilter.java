package com.lkz.blog.web.controller.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkz.blog.web.common.RespPojo;
import com.lkz.blog.web.config.BlogConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * 防止重复提交过滤器，实现方法是利用httpSession做标记，请求第一次进入程序，当前过滤器匹配到请求路径是受保护的路径，
 * 首先看httpSession中有没有此值，如果没有就是第一次访问，然后将此路径作为session的key，当前时间戳做为value存入httpSession中，
 * 等第二次匹配到此路径将能获取到，获取到对应的上次访问事件戳，然后和当前时间对比看是否在不允许请求的时间内，如果不允许就返回提示，
 * 如果允许访问就刷新httpSession中的值，如果业务处理失败，可以在业务中删除此Session，下次访问就是理论上的第一次访问，此拦截器将放行。
 *
 * 可以在配置文件中配置需要拦截的重复提交的路径，底层使用servlet的过滤器实现路径匹配
 *
 * @author 盛世繁花
 */
@Component
public class RepeatSubmissionFilter implements Filter {

    @Resource
    private BlogConfigProperties properties;

    private Logger logger = LoggerFactory.getLogger(RepeatSubmissionFilter.class);


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        HttpSession session= req.getSession();
        String requestURL = req.getRequestURL().toString();

        //先看session中有没有
        Object attribute = session.getAttribute(requestURL);
        //如果session中没有，就代表逻辑上第一次访问，加入值然后放行
        if (attribute==null){
            session.setAttribute(requestURL,new Date().getTime());
            chain.doFilter(request,response);
            return;
        }
        //如果session中有，先看时间是否在不允许访问的时间内
        long per = properties.getRepeatSubmissionSeconds()*1000L;
        long delayTime = new Date().getTime()-(long)attribute;
        //如果在时间范围内不允许访问
        if (delayTime<=per){
            resp.setContentType("text/html;charset=utf-8");
            String result = new ObjectMapper().writeValueAsString(RespPojo.exception("您的操作太频繁了，请稍后再试"));
            logger.debug("来自"+req.getRemoteHost()+"的频繁请求被拒绝");
            resp.getWriter().write(result);
            return;
        }
        //如果此请求在不在时间内，就重新设置一下，然后再放行
        session.setAttribute(requestURL,new Date().getTime());
        chain.doFilter(request,response);
        return;
    }
}
