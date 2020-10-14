package com.lkz.blog.web.mapper;

import com.lkz.blog.pojo.Article;

import java.util.List;

public interface ArticleMapper {
    int deleteByPrimaryKey(Long articleId);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(Long articleId);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    List<Article> getAllBlogHeader(int isAuth);

    //如果auth=0则查找出隐私文章
    List<Article> getBlogPreview(int from,int numbers,int auth);

    //查找指定分类下的文章标题
    List<Article> getSpecifiedCategoryArticleHeader(int categoryId,int isAuth);
}