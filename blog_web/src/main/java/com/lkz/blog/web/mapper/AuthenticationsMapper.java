package com.lkz.blog.web.mapper;

import com.lkz.blog.pojo.Authentications;

public interface AuthenticationsMapper {
    int deleteByPrimaryKey(Integer authenticationsId);

    int insert(Authentications record);

    int insertSelective(Authentications record);

    Authentications selectByPrimaryKey(Integer authenticationsId);

    int updateByPrimaryKeySelective(Authentications record);

    int updateByPrimaryKey(Authentications record);
}