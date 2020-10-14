package com.lkz.blog.web.webService.impl;

import com.lkz.blog.pojo.User;
import com.lkz.blog.web.mapper.RoleMapper;
import com.lkz.blog.web.mapper.UserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BlogUserDetailsService implements UserDetailsService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username==null||username==""){
            throw new UsernameNotFoundException("没有这个人谢谢");
        }
        User user = new User();
        user.setNickName(username);
        user.setNeedAuth(true);
        user.setNeedRoles(true);
        User user1 = userMapper.selectUser(user);
        if (user1== null){
            throw new UsernameNotFoundException("未找到该用户");
        }
        return user1;
    }
}
