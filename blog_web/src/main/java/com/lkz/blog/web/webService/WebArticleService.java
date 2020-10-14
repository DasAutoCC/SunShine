package com.lkz.blog.web.webService;

import com.lkz.blog.pojo.Article;

import java.util.List;

public interface WebArticleService {

    int insertArticle(Article record);

    List<Article> getAllBlogList(int isAuth);

    List<Article> getBlogPreview(int pageNumber,int auth);

    Article selArticleByPrimaryKey(long articleId);

    int updArticleById(Article article);

    /**
     * 获取指定类别下的所有文章id和文章的标题
     * @param categoryId
     * @param isAuth
     * @return
     */
    List<Article> getSpecifiedCategoryArticleHeader(int categoryId,int isAuth);
}
