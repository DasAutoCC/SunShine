package com.lkz.blog.web.mapper;

import com.lkz.blog.pojo.Authentication;

public interface AuthenticationMapper {
    int deleteByPrimaryKey(Integer authenticationId);

    int insert(Authentication record);

    int insertSelective(Authentication record);

    Authentication selectByPrimaryKey(Integer authenticationId);

    int updateByPrimaryKeySelective(Authentication record);

    int updateByPrimaryKey(Authentication record);
}