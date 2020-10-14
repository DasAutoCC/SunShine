package com.lkz.blog.web.utils;

import javax.servlet.http.Cookie;

public class CookieUtil {
    /**
     * 从给出的cookies中找到与theCookieName相等的cookie，找不到返回null
     * @param cookies 原始cookies
     * @param theCookieName 指定的cookieName
     * @return Cookie 或者 null
     */
    public static Cookie findTheCookie(Cookie[] cookies,String theCookieName){
        if (null == cookies){
            return null;
        }
        for (Cookie theCookie:cookies) {
            if (theCookieName.equals(theCookie.getName())){
                return theCookie;
            }
        }
        return null;
    }
}
