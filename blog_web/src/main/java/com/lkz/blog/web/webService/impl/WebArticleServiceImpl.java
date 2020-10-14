package com.lkz.blog.web.webService.impl;

import com.lkz.blog.pojo.Article;
import com.lkz.blog.web.mapper.ArticleMapper;
import com.lkz.blog.web.webService.WebArticleService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class WebArticleServiceImpl implements WebArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private RedisTemplate<String,Long> redisTemplate;

    @Override
    public int insertArticle(Article record) {
        record.setCreateTime(new Date());
        record.setUpdateTime(new Date());
        record.setHavaComment(0);
        record.setIsDelete((byte)0);
        return articleMapper.insert(record);
    }
    //返回仅仅包含博客标题信息的实例
    @Override
    public List<Article> getAllBlogList(int isAuth) {
        List<Article> allBlogHeader = articleMapper.getAllBlogHeader(isAuth);
        return allBlogHeader;
    }

    //分页显示article；返回对象只包含id ，content，preview
    @Override
    public List<Article> getBlogPreview(int pageNumber,int auth) {
        if (pageNumber<=0){
            return null;
        }
        //从第几个开始
        int start = 0;
        //每页几个
        int page = 8;

        if (pageNumber == 1){
            start = 0;
        }else{
            start = (pageNumber-1)*8;
        }
        return articleMapper.getBlogPreview(start, page, auth);
    }

    //根据id获取article对象，包含对象除了preview的所有信息
    @Override
    public Article selArticleByPrimaryKey(long articleId) {
        return articleMapper.selectByPrimaryKey(articleId);
    }

    @Override
    public int updArticleById(Article article){
        if (article.getArticleId() == null){
            return 0;
        }
        return articleMapper.updateByPrimaryKeySelective(article);
    }

    @Override
    public List<Article> getSpecifiedCategoryArticleHeader(int categoryId, int isAuth) {
        List<Article> specifiedCategoryArticleHeader = articleMapper.getSpecifiedCategoryArticleHeader(categoryId, isAuth);
        return specifiedCategoryArticleHeader;
    }

}
