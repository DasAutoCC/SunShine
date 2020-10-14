package com.lkz.blog.pojo;

import java.io.Serializable;

public class Authentication implements Serializable {
    private Integer authenticationId;

    private Long userId;

    private Integer authenticationsId;

    private String authenticationContent;

    public Integer getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(Integer authenticationId) {
        this.authenticationId = authenticationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getAuthenticationsId() {
        return authenticationsId;
    }

    public void setAuthenticationsId(Integer authenticationsId) {
        this.authenticationsId = authenticationsId;
    }

    public String getAuthenticationContent() {
        return authenticationContent;
    }

    public void setAuthenticationContent(String authenticationContent) {
        this.authenticationContent = authenticationContent == null ? null : authenticationContent.trim();
    }

    @Override
    public String toString() {
        return "Authentication{" +
                "authenticationId=" + authenticationId +
                ", userId=" + userId +
                ", authenticationsId=" + authenticationsId +
                ", authenticationContent='" + authenticationContent + '\'' +
                '}';
    }
}