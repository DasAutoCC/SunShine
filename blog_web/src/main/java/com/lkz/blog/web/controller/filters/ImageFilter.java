package com.lkz.blog.web.controller.filters;


import com.lkz.blog.web.config.BlogConfigProperties;
import com.lkz.blog.web.utils.PathUtil;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

@Component
public class ImageFilter implements Filter {

    @Resource
    private BlogConfigProperties blogConfigProperties;

    private Logger logger = LoggerFactory.getLogger(ImageFilter.class);

    private String separator = File.separator;


    private StringBuffer obtainSecondDomain(StringBuffer origin){
        for (int i = 0 ; i< 3;i++){
            int g = origin.indexOf("/");
            origin.delete(0,g+1);
        }
        int i = origin.indexOf("/");
        if (i == -1){
            return origin;
        }
        origin.delete(i,origin.length());
        return origin;
    }

    /**
     * 匹配给出的请求网址是否与受信任的域名列表相匹配
     * @param domainNames:想要匹配的所有域名 ,currentRequestUrl:未经处理的直接请求路径类似https://spring.io/springSecurity/doc
     *                   此方法将截取域名部分进行匹配,其他部分将舍去
     * @return true有匹配的,false无匹配
     */
    public boolean matchDomainName(List<String> domainNames,String currentRequestUrl){
        boolean result = false;
        int first = currentRequestUrl.indexOf("/");
        String substring = currentRequestUrl.substring(first+2);
        int second = substring.indexOf("/");
        String substring2=substring;
        if (second!=-1){
            substring2 = substring.substring(0, second);
        }
        for (String cur:domainNames) {
            if(cur.equals(substring2)){
                result=true;
                break;
            }
        }
        return result;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        StringBuffer requestURL = req.getRequestURL();
        StringBuffer secondDomain  = new StringBuffer(requestURL.toString());
        secondDomain = obtainSecondDomain(secondDomain);
        if (!secondDomain.toString().equals("protect")){
            chain.doFilter(request,response);
            return;
        }
        Enumeration<String> headerNames = req.getHeaderNames();
        String ref = "";
        while(headerNames.hasMoreElements()){
            if (headerNames.nextElement().equals("referer")){
                ref = req.getHeader("referer");
                break;
            }
        }
        if (ref==null || ref.equals("")){
            chain.doFilter(request,response);
            return;
        }
        boolean b = matchDomainName(Arrays.asList(blogConfigProperties.getDomainNames()), ref);

        if (!b){
            logger.debug("来自"+ref+"的请求想要盗链(已拒绝!)");
            System.out.println(ref);
            resp.setCharacterEncoding("utf-8");
            resp.setContentType("image/jpg");
            //获取图片
            URL path = PathUtil.getPath(ImageFilter.class);
            String realPath = path.toString()+"protect/防盗链展示图片/防盗链.jpg";
            System.out.println("获取防盗链图片从:"+realPath);

            URL filePath = new URL(realPath);
            URI uri = null;
            try {
                uri = filePath.toURI();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            File img = new File(uri);
            if (!img.isFile()){
                System.out.println(img.getPath()+"不存在？");
                logger.debug("获取文件夹下的图片失败,"+realPath+"路径不是一个文件！");
                return;
            }
            byte[] bytes = FileUtils.readFileToByteArray(img);
            ServletOutputStream outputStream = resp.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            outputStream.close();
            return;
        }
        chain.doFilter(request,response);
    }

}
