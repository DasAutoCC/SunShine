package com.lkz.blog.pub.service.pubDaoService;

import com.lkz.blog.pojo.User;

public interface PubUserDaoService {
    User selectUser(User userOnly) throws RuntimeException;
}
