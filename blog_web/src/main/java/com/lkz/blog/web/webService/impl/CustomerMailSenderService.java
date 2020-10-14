package com.lkz.blog.web.webService.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CustomerMailSenderService {

    @Resource
    private JavaMailSender javaMailSender;

    private static final Logger logger = LoggerFactory.getLogger(CustomerMailSenderService.class);

    public String sendVerifMsg(String toMail,String verifCode){
        SimpleMailMessage sm = new SimpleMailMessage();
        String errorMsg = "";
        sm.setSubject("[盛世繁花]欢迎您的注册！这里给您献上验证码");
        sm.setFrom("shengshifanhua@foxmail.com");
        sm.setTo(toMail);
        sm.setText("非常欢迎您在我的网站注册！\n验证码在这里："+verifCode+"\n希望在以后的日子里[盛世繁花]能和您一路相伴！共同前行！");
        try{
            javaMailSender.send(sm);
        }catch (Exception e){
            e.printStackTrace();
            return "用的是企鹅的邮箱，有时候会连接失败，重试一下就好了";
        }
        return "";
    }

    public String sendCustomerMsg(String toMail,String msg){
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setFrom("shengshifanhua@foxmail.com");
        sm.setTo(toMail);
        sm.setText(msg);
        try{
            logger.info("正在向邮箱:"+toMail+"发送邮件，邮件内容为"+sm);
            javaMailSender.send(sm);
            logger.info("已成功向邮箱:"+toMail+"发送邮件，邮件内容为"+sm);
        }catch (Exception e){
            e.printStackTrace();
            logger.info("向邮箱:"+toMail+"发送邮件失败，邮件内容为"+sm);
            return "用的是企鹅的邮箱，有时候会连接失败，重试一下就好了";
        }
        return "";
    }
}
