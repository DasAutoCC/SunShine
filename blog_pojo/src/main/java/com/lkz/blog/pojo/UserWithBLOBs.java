package com.lkz.blog.pojo;

public class UserWithBLOBs extends User {
    private String declaration;

    private String userHead;

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration == null ? null : declaration.trim();
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead == null ? null : userHead.trim();
    }

    @Override
    public String toString() {

        return super.toString()+"UserWithBLOBs{" +
                "declaration='" + declaration + '\'' +
                ", userHead='" + userHead + '\'' +
                '}';
    }
}