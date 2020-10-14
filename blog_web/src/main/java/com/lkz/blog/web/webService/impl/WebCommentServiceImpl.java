package com.lkz.blog.web.webService.impl;

import com.lkz.blog.pojo.Comment;
import com.lkz.blog.web.exception.ArgumentException;
import com.lkz.blog.web.mapper.CommentMapper;
import com.lkz.blog.web.webService.WebCommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class  WebCommentServiceImpl implements WebCommentService {

    @Resource
    CommentMapper commentMapper;

    private static final Logger logger = LoggerFactory.getLogger(WebCommentServiceImpl.class);

    @Resource
    RedisTemplate<String,Comment> redisTemplate;

    /**
     * 将评论先存入redis中，等待审核通过后再写入数据库
     * @param comment
     * @return
     */
    @Override
    public int insComment(Comment comment) {
        Assert.notNull(comment,"参数异常");
        comment.setCreateTime(new Date());
        comment.setIsDelete((byte)0);
        //所有评论暂时放入Redis中等待审核
        BoundListOperations<String, Comment> commentBoundListOperations = redisTemplate.boundListOps("comments");
        commentBoundListOperations.leftPush(comment);
        return 1;
    }

    /**
     *
     * @param blogId
     * @return
     */
    @Override
    public List<Comment> selComment(Long blogId) {
        List<Comment> comments = commentMapper.selCommentWithUserByBlogId(blogId);
        return comments;
    }

    /**
     * 此方法是对外暴漏的方法
     * @param comments
     * @return
     */
    @Override
    public List<Comment> sortComments(List<Comment> comments) {
        if (comments == null){
            throw new ArgumentException("参数为null");
        }
        List<Comment> comments1 = sortingCommentByTime(comments);
        return comments1;
    }

    /**
     * 实现方式是以当前用户的userId为key在redis中新建一个当前用户获取到的审核数据的List副本，
     * 原数据中的数据直接弹出，这样就解决了并发修改redis数据会出现的未知问题
     * 如果检查到用户在获取数据之前redis中有未审核完成的就暂时不获取更新的数据，仅将旧数据返回
     * @param num 一批获取几个
     * @param userIdKey 当前获取数据用户的id
     * @return List类型，如果如果待审核评论总个数为0将返回null，
     *         如果待审核评论总个数不足num个但是多余0个，不足的List数据将以null填充
     */
    @Override
    public List<Comment> getUnCheckedComment(int num,long userIdKey) {
        BoundListOperations<String, Comment> commentBoundListOperations = redisTemplate.boundListOps(String.valueOf(userIdKey));
        List<Comment> result = null;
        Long userSize = commentBoundListOperations.size();
        Long mainSize = null;
        //如果userIdKey中没有数据
        if (userSize==null || userSize<=0 ){
            //初始化comments的size，执行以下逻辑
            mainSize=redisTemplate.opsForList().size("comments");
            if (mainSize == null || mainSize <=0){
                return null;
            }
            //直接全部弹出mainSize个数据并写入userIdKey，因为Redis是单线程，不用考虑同步问题
            result = new ArrayList<>();
            for (int i = 0 ;i<(mainSize>=num ? num : mainSize); i++){
                //从main中弹出
                Comment comment = redisTemplate.opsForList().rightPop("comments");
                //设置唯一标记
                comment.setCommentId(i*1L);
                //加入userIdKey中
                commentBoundListOperations.rightPush(comment);
                //放入结果中
                result.add(comment);

            }
            return result;
        }

        //如果userIdKey有数据
        if (userSize !=null && userSize>0){
            //如果数据个数小于请求的个数num
            result = commentBoundListOperations.range(0, num);
            //这里不用在设置唯一标记了，因为如果有数据的话肯定是执行过没有数据的逻辑，没有数据的逻辑已经设置了唯一标记了
            //所以这里直接返回就行
            return result;
        }

        return null;
    }

    /**
     * @param commentId 此处的id代表的是当前comment对象在redis中的List数据结构的索引值，并不是comment对象的id
     * @param isPassed 对当前comment的操作，布尔包装类型，true/false代表是否通过审核
     * @param userIdKey 当前操作数据用户的id
     * @return 返回受影响的行数
     */
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int doCheckComment(long commentId, boolean isPassed, long userIdKey) {
        String key = String.valueOf(userIdKey);
        List<Comment> range = redisTemplate.opsForList().range(key, 0, -1);
        if (range == null || range.size()<=0){
//            throw  new RuntimeException("当前没有待检查的评论"); 抛出异常或者返回0
            return  0;
        }
        for (int i = 0 ; i<range.size(); i++) {
            //具体逻辑
            if (range.get(i).getCommentId().longValue() == commentId){

                //如果此评论不进行放行操作则直接将其从redis中移除并返回操作成功标志:1
                if (!isPassed){
                    redisTemplate.opsForList().remove(key,1,range.get(i));
                    return 1;
                }
                Comment comment = range.get(i);
                //清除redis中的同一个数据
                redisTemplate.opsForList().remove(key,1,range.get(i));
                //清除当前评论的主键，然后再插入数据库
                comment.setCommentId(null);
                int insert = commentMapper.insert(comment);

                return insert;
            }
        }
        return 0;
    }

    /**
     * 找到给出的comment对象的祖先对象
     * @param comment
     * @param data
     * @return
     */
    private  Comment findTopLevelComment(Comment comment , List<Comment> data){
        //首先判断当前评论是否为回复，如果不是回复直接返回当前comment
        if (comment.getReplyFor()==0){
            return comment;
        }
        //记录当前comment的replyFor
        Long replyFor = comment.getReplyFor();
        //记录当前comment的CommentId，用来检查循环完成后是否找到comment的father
        Long copyId = comment.getCommentId();
        for (Comment cur: data) {
            if (cur.getCommentId().equals(replyFor)){
                comment = cur;
                break;
            }
        }
        //如果循环完成后检查到当前comment对象未发生变化时即未找到当前comment的父级评论，此异常无法在此解决，抛出异常
        //策略发生变化，如果未找到就返回null并不抛出异常
        if (comment.getCommentId().equals(copyId)){
            logger.debug("数据结构异常，"+comment+"有父级评论，但是在对象"+data+"中找不到它的父级评论");
            return null;
            //throw new RuntimeException("数据结构异常，"+comment+"有父级评论，但是在对象"+data+"中找不到它的父级评论");
        }
        return findTopLevelComment(comment,data);
    }

    /**
     * 找到所有的不是回复的评论
     * @param data
     * @return
     */
    private List<Comment> findNotReplyComments(List<Comment> data){
        List<Comment> result = new ArrayList<>();
        for (Comment or: data) {
            if (or.getReplyFor() == 0){
                or.setReplies(new ArrayList<>());
                result.add(or);
            }
        }
        return result;
    }

    /**
     * 将所有comment排序
     * @param data
     * @return
     */
    private List<Comment> sortingCommentByTime(List<Comment> data){

        //直接找出所有顶级评论
        List<Comment> result = findNotReplyComments(data);
        List<Comment> badDates = new ArrayList<>();

        for (Comment com:data) {
            Comment topLevelComment = findTopLevelComment(com, data);
            //如果未找到此评论的顶级评论或者本身就是顶级评论的话，什么也不做然后跳出本次循环
            if (com == topLevelComment || topLevelComment == null){
                if (topLevelComment == null){
                    badDates.add(com);
                }
                continue;
            }
            //找出所有非顶级评论的顶级评论，然后将自己放入顶级评论对象中的List对象中
            for (Comment o:result) {
                if (o.getCommentId().equals(topLevelComment.getCommentId())){
                    o.getReplies().add(com);
                }
            }
        }
        //将坏数据暂时存入评论的末尾
        if (badDates.size()>0){
            logger.debug("发现坏评论："+badDates);
            for (Comment badDate: badDates) {
                result.add(badDate);
            }
        }
        return result;
    }

}
