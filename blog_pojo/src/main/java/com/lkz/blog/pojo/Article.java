package com.lkz.blog.pojo;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {
    private Long articleId;

    private Long userId;

    private String articleHeader;

    private Integer havaComment;

    private Date createTime;

    private Date updateTime;

    private Byte isDelete;

    private Byte isPublic;

    private String articleContent;

    private String preview;

    private Integer topLevel;

    public Integer getTopLevel() {
        return topLevel;
    }

    public void setTopLevel(Integer topLevel) {
        this.topLevel = topLevel;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getArticleHeader() {
        return articleHeader;
    }

    public void setArticleHeader(String articleHeader) {
        this.articleHeader = articleHeader == null ? null : articleHeader.trim();
    }

    public Integer getHavaComment() {
        return havaComment;
    }

    public void setHavaComment(Integer havaComment) {
        this.havaComment = havaComment;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public Byte getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Byte isPublic) {
        this.isPublic = isPublic;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public void setArticleContent(String articleContent) {
        this.articleContent = articleContent == null ? null : articleContent.trim();
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", userId=" + userId +
                ", articleHeader='" + articleHeader + '\'' +
                ", havaComment=" + havaComment +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDelete=" + isDelete +
                ", isPublic=" + isPublic +
                ", articleContent='" + articleContent + '\'' +
                ", preview='" + preview + '\'' +
                ", topLevel=" + topLevel +
                '}';
    }
}