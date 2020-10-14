package com.lkz.blog.web.controller;

import com.lkz.blog.pojo.Article;
import com.lkz.blog.pojo.Comment;
import com.lkz.blog.pojo.User;
import com.lkz.blog.web.common.RespPojo;
import com.lkz.blog.web.webService.WebArticleService;
import com.lkz.blog.web.webService.WebCommentService;
import com.lkz.blog.web.webService.impl.CustomerMailSenderService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;

@RestController
public class CommentController {

    @Resource
    private WebCommentService commentService;

    @Resource
    private WebArticleService webArticleService;

    @Resource
    private CustomerMailSenderService mailSenderService;

    @Resource
    private ExecutorService executorService;

    @RequestMapping("/insComment")
    public RespPojo insComment(Comment comment, Authentication authentication){
        if (comment == null || comment.getBlogId() == null || comment.getCommentContent() == null ||authentication == null){
            return RespPojo.exception("参数有误，请核对");
        }
        System.out.println(comment.getReplyFor());
        //如果用户未登录就发表评论的解决方案
        //此处可以使用springSecurity配置权限
        if (authentication == null){
            return RespPojo.failed("您未认证，无法评论");
        }
        //如果此条评论不是回复，则设置字段
        if (comment.getReplyFor() == null){
            comment.setReplyFor(0L);
        }
        //设置评论人id
        comment.setCommentUser(((User)authentication.getPrincipal()).getUserId());
        int i = commentService.insComment(comment);
        if (i == 1){
            executorService.execute(() -> mailSenderService.sendCustomerMsg("1321200962@qq.com","有人发表了新评论:"+comment.getCommentContent()));
            return RespPojo.success(null);
        }
        return RespPojo.failed("添加失败,受影响的行数为"+i);
    }

    @RequestMapping("/selComment")
    public RespPojo selComment(Long blogId ,Authentication authentication){

        //判断是否有这个文章
        Article article = webArticleService.selArticleByPrimaryKey(blogId);
        if (article == null || article.getIsPublic() == null){
            return RespPojo.success("当前文章暂无评论");
        }
        //判断用户权限是否可以查看评论
        //如果当前文章是隐私文章
        if (article.getIsPublic()==0){
            //校验当前用户是否有权查看此文章
            if (authentication==null){
                return RespPojo.exception("您无权查看评论");
            }
            User pri = (User)authentication.getPrincipal();
            if (pri.getUserId()!=1){
                return RespPojo.exception("您无权查看评论");
            }
        }

        //在这获取了一下评论，如果没有就不麻烦业务进行排序操作了
        List<Comment> comments = commentService.selComment(blogId);
        if (comments == null || comments.size()<=0){
            return RespPojo.success("当前文章暂无评论");
        }
        //如果有评论就进行递归
        return RespPojo.success(commentService.sortComments(comments));
    }

    //获取我尚未检查的评论
    @RequestMapping("/getUncheckedComment")
    @ResponseBody
    public RespPojo getMyUnCheckedComment(Authentication authentication){

        List<Comment> unCheckedComment = commentService.getUnCheckedComment(10, ((User) authentication.getPrincipal()).getUserId());

        return RespPojo.success(unCheckedComment);

    }

    @ResponseBody
    @RequestMapping("/doCheckComment")
    public RespPojo doCheckComment(Authentication authentication,int commentRedisId,Boolean isPassed/*如果是null就是暂未确定是否能让他通过，执行null对应的逻辑*/){
        int i = commentService.doCheckComment(commentRedisId, isPassed, ((User) authentication.getPrincipal()).getUserId());
        return RespPojo.success(i);
    }

}
