package com.lkz.blog.web.controller.advice;

import com.lkz.blog.web.exception.ArgumentException;
import com.lkz.blog.web.common.RespPojo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * 统一异常处理
 * @author 盛世繁花
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @Resource
    private JavaMailSender mailSender;

    @ResponseBody
    @ExceptionHandler(ArgumentException.class)
    public RespPojo restArgumentException(ArgumentException e){
        logger.warn("请求参数异常:"+e.getErrorMessage());
        return RespPojo.exception(e.getErrorMessage());
    }
    @ExceptionHandler(Exception.class)
    public void allException(Exception e, HttpServletRequest request, HttpServletResponse response){
        e.printStackTrace();
        logger.warn("处理请求失败:"+e.getMessage());
        try {
            request.getRequestDispatcher("/error").forward(request,response);
        } catch (ServletException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return;

    }
    @ExceptionHandler(Error.class)
    public void catchErrorAndSendMsgToMe(Error error, HttpServletRequest request, HttpServletResponse response){
        //进行通知管理员处理。。。可以使用阿里云的短信服务给管理员发个邮件
        String message = error.getMessage();
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setSubject("服务器出error了");
        sm.setFrom("shengshifanhua@foxmail.com");
        sm.setTo("1321200962@qq.com");
        sm.setText(message);
        try {
            request.getRequestDispatcher("/error").forward(request,response);
        } catch (ServletException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return;
    }

}
