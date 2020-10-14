package com.lkz.blog.web.webService.impl;

import com.lkz.blog.pojo.Authentication;
import com.lkz.blog.pojo.User;
import com.lkz.blog.web.config.BlogConfigProperties;
import com.lkz.blog.web.exception.ArgumentException;
import com.lkz.blog.web.mapper.AuthenticationMapper;
import com.lkz.blog.web.mapper.UserMapper;
import com.lkz.blog.web.utils.BlogStringUtil;
import com.lkz.blog.web.webService.WebUserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

@Service
public class WebUserServiceImpl implements WebUserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private AuthenticationMapper authenticationMapper;

    @Resource
    private BlogConfigProperties configProperties;

    @Override
    public User selectUser(User record) throws RuntimeException {
        if (record.getNickName() == null || record.getNickName() == ""){
            throw new UsernameNotFoundException("查询参数有误，请核对后再试！");
        }
        User user = userMapper.selectUser(record);
        if (user== null){
            throw  new UsernameNotFoundException("未找到该用户");
        }
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertUserAndSignIn(User record ,Authentication userAuth) throws RuntimeException {
        Random rm = new Random();

        //组装user
        if (record.getSex() == null){
            //设置默认性别为男
            record.setSex(true);
        }
        if (record.getSex()){
            int k = rm.nextInt();
            int j = Math.abs(k % configProperties.getDefaultManHeadURL().length);
            record.setUserHead(configProperties.getDefaultManHeadURL()[j]);
        }else {
            rm = new Random();
            int k = rm.nextInt();
            int j = Math.abs(k % configProperties.getDefaultGirlHeadURL().length);
            record.setUserHead(configProperties.getDefaultGirlHeadURL()[j]);

        }


        record.setRole(2);
        record.setIsDelete((byte)1);
        record.setRegisterTime(new Date());
        record.setUserStatus(1);
        int i = userMapper.insertUser(record);
        if (i!=1){
            return 0;
        }
        User user = userMapper.selectUser(record);
        Long userId = user.getUserId();
        userAuth.setUserId(userId);
        userAuth.setAuthenticationsId(1);
        int insert = authenticationMapper.insert(userAuth);
        if (insert != 1){
            return 0;
        }
        return 1;
    }

    @Override
    public int insertUser(User record) {
        return userMapper.insertUser(record);
    }

    @Override
    public int selectCountByNickName(String nickName) {
        int i = userMapper.selectCountByNickName(nickName);
        return i;
    }

    /**
     * 问数据库有没有这个邮箱
     * @param userEmail
     * @return
     */
    @Override
    public int selectCountByEmail(String userEmail) {
        return userMapper.selectCountByEmail(userEmail);
    }
}
