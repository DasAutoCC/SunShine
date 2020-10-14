package com.lkz.blog.web.utils;

import org.springframework.boot.system.ApplicationHome;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;

public class PathUtil {
    /**
     * 获取当前包的根路径所在的路径，例如com.springframework.spring.core,获取的路径时这个包所在的根路径
     * 如果当前程序运行在jar包中，得到的会是jar包内路径，而不是相对系统的路径
     * @param clazz
     * @return
     */
    public static URL getPath(Class clazz){
        URL resource = clazz.getClassLoader().getResource("");
        return resource;
    }

    /**
     * 获取当前程序的根目录，jar包所在的绝对路径，无关平台
     * @return
     */
    public static String getJarFileAbsolutePathAtCurrentSystem() throws UnsupportedEncodingException {
        String jarWholePath = PathUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile();
        jarWholePath = java.net.URLDecoder.decode(jarWholePath, "UTF-8");
        String jarPath = new File(jarWholePath).getParentFile().getAbsolutePath();
        return jarPath;
    }
    /**
     *SpringBoot获取当前程序的根目录，jar包所在的绝对路径，无关平台
     */
    public static String tess(){
        ApplicationHome h = new ApplicationHome(PathUtil.class);
        File jarF = h.getSource();
        return jarF.getParentFile().toString();
    }
}
