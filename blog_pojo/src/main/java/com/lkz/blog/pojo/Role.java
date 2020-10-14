package com.lkz.blog.pojo;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

public class Role implements GrantedAuthority, Serializable {
    private Integer roleId;

    private String roleContent;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleContent() {
        return roleContent;
    }

    public void setRoleContent(String roleContent) {
        this.roleContent = roleContent == null ? null : roleContent.trim();
    }

    @Override
    public String getAuthority() {
        return roleContent;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleContent='" + roleContent + '\'' +
                '}';
    }
}