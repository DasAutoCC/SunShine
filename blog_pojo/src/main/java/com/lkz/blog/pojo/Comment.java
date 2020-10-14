package com.lkz.blog.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Comment implements Serializable {
    private Long commentId;

    private Long blogId;

    private Long commentUser;

    private Long replyFor;

    private int starNum;

    private String commentContent;

    private User user;

    private List<Comment> replies;

    private Date createTime;

    private Byte isDelete;

    private String nickName;

    private String userHead;



    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getCommentUser() {
        return commentUser;
    }

    public void setCommentUser(Long commentUser) {
        this.commentUser = commentUser;
    }

    public Long getReplyFor() {
        return replyFor;
    }

    public void setReplyFor(Long replyFor) {
        this.replyFor = replyFor;
    }

    public int getStarNum() {
        return starNum;
    }

    public void setStarNum(int starNum) {
        this.starNum = starNum;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent == null ? null : commentContent.trim();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public List<Comment> getReplies() {
        return replies;
    }

    public void setReplies(List<Comment> replies) {
        this.replies = replies;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", blogId=" + blogId +
                ", commentUser=" + commentUser +
                ", replyFor=" + replyFor +
                ", starNum=" + starNum +
                ", commentContent='" + commentContent + '\'' +
                ", user=" + user +
                ", replies=" + replies +
                ", createTime=" + createTime +
                ", isDelete=" + isDelete +
                ", nickName='" + nickName + '\'' +
                ", userHead='" + userHead + '\'' +
                '}';
    }
}