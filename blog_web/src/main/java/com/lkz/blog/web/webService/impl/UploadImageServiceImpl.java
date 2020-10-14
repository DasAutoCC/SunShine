package com.lkz.blog.web.webService.impl;

import com.lkz.blog.web.utils.PathUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.UUID;


/**
 * 上传图片service,平台无关性上传路径
 * @author 盛世繁花
 */
@Service
public class UploadImageServiceImpl {

    private Logger logger = LoggerFactory.getLogger(UploadImageServiceImpl.class);

    private String separator = File.separator;

    private String getFileSuffix(String originalFilename){
        int i = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(i).toLowerCase();
        return suffix;
    }
    public String uploadImage(MultipartFile file) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String suffix = getFileSuffix(originalFilename);
        String [] inx = {".jpg",".png",".gif"};
        boolean suffexIsPassed = false;
        for (String now:inx) {
            if (now.equals(suffix)){
                suffexIsPassed = true;
                break;
            }
        }
        if (!suffexIsPassed){
            logger.debug("上传的图片类型不合法"+suffix+"参考合法的类型.jpg .png .gif");
            return null;
        }
        long size = file.getSize();
        //20M
        if (size>1*1024*1024*20){
            logger.info("上传的文件大小超出限制（20MB");
            return null;
        }
        String fileName = "";
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        String[] split = s.split("-");
        StringBuilder sb = new StringBuilder();
        for (String now: split) {
            sb.append(now);
        }
        fileName = sb.toString()+suffix;
//        String uri = PathUtil.getJarFileAbsolutePathAtCurrentSystem();
        String uri = PathUtil.tess();
        logger.debug("获取到的路径为:"+uri);
        uri = uri+separator+"protect"+separator+"img"+separator;
        logger.debug("存储的目标路径为:"+uri);
        logger.debug("生成的文件名为:"+fileName);
        uri = uri+fileName;
//        "/usr/local/blogDir/file:/usr/local/blogDir/blog_web-1.0-SNAPSHOT.jar!/BOOT-INF/protect/img/124d1f1eece84de6b9a92430ea910281.jpg"
//        D:\02-IdeaProject\blog\blog_web\target\file:\D:\02-IdeaProject\blog\blog_web\target\blog_web-1.0-SNAPSHOT.jar!\BOOT-INF
        logger.debug("将要存储的文件路径为:"+uri);

        File doFile = new File(uri);
        if (doFile.exists()){
            doFile.createNewFile();
        }
        FileUtils.copyInputStreamToFile(file.getInputStream(),doFile);
        logger.debug("存储完毕，存储路径为:"+uri);
        return fileName;
    }

    public File getTheProtectedFile(String fileName) {

        //获取文件路径
        String uri = PathUtil.tess()+separator+"protect"+separator+"img"+separator+fileName;
        File doFile = new File(uri);
        if (!doFile.exists()){
            logger.debug("请求的文件不存在:"+uri);
            return null;
        }
        return doFile;
    }

}
