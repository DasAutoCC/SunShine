package com.lkz.blog.web.mapper;

import com.lkz.blog.pojo.Comment;

import java.util.List;

public interface CommentMapper {
    int deleteByPrimaryKey(Long commentId);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Long commentId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    List<Comment> selCommentByBlogId(Long blogId);

    List<Comment> selCommentWithUserByBlogId(Long blogId);
}