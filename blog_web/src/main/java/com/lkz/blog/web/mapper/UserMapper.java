package com.lkz.blog.web.mapper;

import com.lkz.blog.pojo.User;
import com.lkz.blog.pojo.UserWithBLOBs;

public interface UserMapper {

    int deleteByPrimaryKey(Long userId);

    @Deprecated
    int insert(UserWithBLOBs record);

    @Deprecated
    int insertSelective(UserWithBLOBs record);

    @Deprecated
    UserWithBLOBs selectByPrimaryKey(Long userId);

    @Deprecated
    int updateByPrimaryKeySelective(UserWithBLOBs record);

    @Deprecated
    int updateByPrimaryKeyWithBLOBs(UserWithBLOBs record);

    int updateByPrimaryKey(User record);

    User selectUser(User record);

    int insertUser(User user);

    public int selectCountByNickName(String nickName);

    public int selectCountByEmail(String userEmail);

}