package com.lkz.blog.web.webService;

import com.lkz.blog.pojo.Comment;

import java.util.List;

public interface WebCommentService {

    /**
     * 评论有审核机制，评论首先用敏感词过滤器过滤一下，如果有风险就不进行数据库插入操作
     * 目前只有简单实现，有人评论后先放入Redis中，人工审核完成后再插入数据库
     * 审核机制包括敏感词过滤、网址过滤、广告过滤、发言次数限制
     * 必须是已登录用户才能发表评论
     * @param comment
     * @return
     */
    int insComment(Comment comment);

    /**
     * 仅仅查出当前博客下的所有评论，无排序
     * @param blogId
     * @return
     */
    List<Comment> selComment(Long blogId);

    /**
     * 将评论进行排序，直接用于前端显示
     * @param comments
     * @return
     */
    List<Comment> sortComments(List<Comment> comments);

    /**
     * 获取还未审核的评论，成批次获取，审核完一批再请求下一批
     * 实现方式具体见实现类中的细节
     * @param num 一批获取几个
     * @param userIdKey 当前获取数据用户的id
     * @return 返回数据数组
     */
    List<Comment> getUnCheckedComment(int num,long userIdKey);

    /**
     * 对redis中数据做实际操作，是否通过审核
     * 具体实现方式见实现类中的细节
     * @param commentId 此处的id代表的是当前comment对象在redis中的List数据结构的索引值，并不是comment对象的id
     * @param isPassed 对当前comment的操作，布尔包装类型，true/false代表是否通过审核
     * @param userIdKey 当前操作数据用户的id
     * @return
     */
    int doCheckComment(long commentId ,boolean isPassed,long userIdKey);

}
