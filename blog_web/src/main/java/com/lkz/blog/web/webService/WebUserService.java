package com.lkz.blog.web.webService;

import com.lkz.blog.pojo.Authentication;
import com.lkz.blog.pojo.User;

public interface WebUserService {

    public User selectUser(User record) throws RuntimeException;

    public int insertUserAndSignIn(User record , Authentication userAuth) throws RuntimeException;

    public int insertUser(User record);

    public int selectCountByNickName(String nickName);

    public int selectCountByEmail(String userEmail);

}


