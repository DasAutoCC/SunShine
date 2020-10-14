package com.lkz.blog.pojo;

import java.io.Serializable;

public class Authentications implements Serializable {
    private Integer authenticationsId;

    private String authenticationsType;

    public Integer getAuthenticationsId() {
        return authenticationsId;
    }

    public void setAuthenticationsId(Integer authenticationsId) {
        this.authenticationsId = authenticationsId;
    }

    public String getAuthenticationsType() {
        return authenticationsType;
    }

    public void setAuthenticationsType(String authenticationsType) {
        this.authenticationsType = authenticationsType == null ? null : authenticationsType.trim();
    }
}