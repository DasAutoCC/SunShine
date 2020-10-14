package com.lkz.blog.pojo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class User implements UserDetails, Serializable {
    private Long userId;

    private String nickName;

    private Long userCount;

    private Boolean sex;

    private String phone;

    private String email;

    private Integer role;

    private Date registerTime;

    private Integer userStatus;

    private Byte isDelete;

    private Date loginTime;

    private String loginIp;

    private String remake;

    private String declaration;

    private String userHead;

    private List<Authentication> authenticationList;

    private List<Role> roles;

    private boolean isNeedRoles;

    private boolean isNeedAuth;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    public String getRemake() {
        return remake;
    }

    public void setRemake(String remake) {
        this.remake = remake == null ? null : remake.trim();
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public List<Authentication> getAuthenticationList() {
        return authenticationList;
    }

    public void setAuthenticationList(List<Authentication> authenticationList) {
        this.authenticationList = authenticationList;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isNeedRoles() {
        return isNeedRoles;
    }

    public void setNeedRoles(boolean needRoles) {
        isNeedRoles = needRoles;
    }

    public boolean isNeedAuth() {
        return isNeedAuth;
    }

    public void setNeedAuth(boolean needAuth) {
        isNeedAuth = needAuth;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", userCount=" + userCount +
                ", sex=" + sex +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", registerTime=" + registerTime +
                ", userStatus=" + userStatus +
                ", isDelete=" + isDelete +
                ", loginTime=" + loginTime +
                ", loginIp='" + loginIp + '\'' +
                ", remake='" + remake + '\'' +
                ", declaration='" + declaration + '\'' +
                ", userHead='" + userHead + '\'' +
                ", authenticationList=" + authenticationList +
                ", roles=" + roles +
                ", isNeedRoles=" + isNeedRoles +
                ", isNeedAuth=" + isNeedAuth +
                '}';
    }

    //这里权限与角色弄混了，
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> authorities = new ArrayList<>();
        for (Role role: roles) {
            authorities.add(role);
        }
        //想添加角色可以在authorities.add("ROLE_ROLENAME")的方式进行添加角色信息
        return authorities;
    }


    @Override
    public String getPassword() {
        String password = "";
        if (authenticationList == null){
            return password;
        }
        for (Authentication aut:authenticationList) {
            if(aut.getAuthenticationsId() == 1){
                password = aut.getAuthenticationContent();
            }
        }
        return password;
    }

    @Override
    public String getUsername() {
        return nickName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isDelete == 1 ? true : false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return userStatus == 1 ? true : false;
    }
}