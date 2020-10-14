package com.lkz.blog.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lkz.blog.pojo.Article;
import com.lkz.blog.pojo.User;
import com.lkz.blog.web.common.RespPojo;
import com.lkz.blog.web.webService.WebArticleService;
import com.lkz.blog.web.webService.impl.UploadImageServiceImpl;
import org.apache.commons.io.FileUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * 关于文章类操作的控制器，因为用到模板引擎暂时不用@RestController
 * @author 盛世繁花
 * @data 2020-8-15
 */
@Controller
public class ArticleController {

    @Resource
    private WebArticleService webArticleService;

    @Resource
    private UploadImageServiceImpl uploadImageService;


    @ResponseBody
    @RequestMapping("/blog/addArticle")
    public RespPojo addArticle(String articleHeader,String articleContent,Byte isPublic, String preview, Authentication securityAuth){
        if (securityAuth==null || securityAuth.getPrincipal()==null){
            return RespPojo.exception("您未认证");
        }
        User principal = (User)securityAuth.getPrincipal();
        if (articleHeader == null || articleContent == null || articleHeader.equals("")  || articleContent.equals("") ){
            return RespPojo.exception("不允许空内容提交");
        }
        if (isPublic == null || isPublic>1){
            isPublic=1;
        }
        Article record =new Article();
        record.setArticleContent(articleContent);
        record.setArticleHeader(articleHeader);
        record.setIsPublic(isPublic);
        record.setPreview(preview);
        record.setUserId(principal.getUserId());
        int i = webArticleService.insertArticle(record);
        if (i == 1){
            return RespPojo.success();
        }
        return RespPojo.failed("新增失败,受影响的行数为"+i);
    }


    @ResponseBody
    @RequestMapping("/blog-list-all")
    public RespPojo allBlogListHeader(Authentication authentication){
        int isAuth = 1;
        if (authentication!=null && authentication.getPrincipal() != null){
            User user = (User)authentication.getPrincipal();
            if (user.getUserId()==1){
                isAuth=0;
            }
        }
        List<Article> allBlogList = webArticleService.getAllBlogList(isAuth);
        List<Map<String,Object>> result = new ArrayList<>();
        for(int i=0 ;i<allBlogList.size() ; i++){
            HashMap<String,Object> res =new HashMap<>();
            res.put("id",allBlogList.get(i).getArticleId());
            res.put("details",allBlogList.get(i).getArticleHeader());
            result.add(res);
        }
        return RespPojo.success(result);
    }

    @ResponseBody
    @RequestMapping("/blog-list")
    public RespPojo blogPreviewList(Integer pageNumber,Authentication authentication){
        if (pageNumber == null || pageNumber == 0){
            return RespPojo.exception("页码有误");
        }
        int isAuth = 1;
        if (authentication !=null && authentication.getPrincipal() != null){
            User user = (User)authentication.getPrincipal();
            if (user.getUserId()==1){
                isAuth=0;
            }
        }
        List<Article> blogPreview = webArticleService.getBlogPreview(pageNumber,isAuth);
        if (blogPreview == null){
            return RespPojo.exception("暂未获取到相关文章");
        }
        //封装一个list对象进行返回
        List<Map<String,Object>> respList = new ArrayList<>();
        for (Article article: blogPreview) {
            Map<String,Object> res = new HashMap<>();
            res.put("id",article.getArticleId());
            res.put("header",article.getArticleHeader());
            res.put("preview",article.getPreview());
            res.put("isTop",article.getTopLevel());
            res.put("createTime",article.getCreateTime().getTime());
            res.put("isPublic",article.getIsPublic());
            respList.add(res);
        }
        return RespPojo.success(respList);
    }

    @ResponseBody
    @RequestMapping("/blog-details")
    public RespPojo getArticleDetails(String blogId,Authentication authentication){
        long l ;
        try{
            l=Long.parseLong(blogId);
        }catch(NumberFormatException e){
            return RespPojo.exception("您输入了非法页码");
        }
        Article article = webArticleService.selArticleByPrimaryKey(l);
        if (article == null){
            return RespPojo.exception("获取文章失败,文章id="+l);
        }
        //如果当前文章是隐私文章
        if (article.getIsPublic()==0){
            //校验当前用户是否有权查看此文章
            if (authentication==null){
                return RespPojo.exception("您无权查看此文章");
            }
            User pri = (User)authentication.getPrincipal();
            if (pri.getUserId()!=1){
                return RespPojo.exception("您无权查看此文章");
            }
        }
        return RespPojo.success(article);
    }

    /**
     * editor.md上传图片接口,以在springSecurity中配置访问权限了
     * @param file
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/upload-image")
    @ResponseBody
    public Map uploadImage(@RequestParam(value = "editormd-image-file", required = true)MultipartFile file, HttpServletRequest request , HttpServletResponse response) throws Exception{

        String message = "";
        String url ;
        Map<String,Object> result = new HashMap<>();
        String s2 = uploadImageService.uploadImage(file);
        if (s2==null){
            message = "文件类型不合法或文件大小超过限制，上传失败";
            result.put("success",0);
            result.put("message",message);
            return result;
        }
        url = "/protect/img/" + s2;
        //1代表成功，0代表失败
        result.put("success",1);
        result.put("message",message);
        result.put("url",url);
        return result;
    }

    /**
     * 设置置顶文章，参数为文章id和文章置顶等级，指定等级为数值类型，数字越大与靠前
     * @param topArticleId 可以转换为long类型的字符串，文章id
     * @param topLevel 可以转换为int类型字符串
     * @return
     */
    @RequestMapping("/setTopArticleId")
    @ResponseBody
    public RespPojo setTopArticle(String topArticleId,String topLevel){
        if (topArticleId==null || topLevel == null){
            return RespPojo.exception("参数有误");
        }

        System.out.println(topArticleId);
        long l = Long.parseLong(topArticleId);
        int i1 = Integer.parseInt(topLevel);
        Article article = new Article();
        article.setArticleId(l);
        article.setTopLevel(i1);
        int i = webArticleService.updArticleById(article);
        if (i!=1){
            return RespPojo.failed("设置失败");
        }
        return RespPojo.success();
    }


    /**
     * 获取要修改的文章的页面，使用了模板引擎
     * @param articleId
     * @param model
     * @param authentication
     * @return
     */
    @GetMapping("/upd-article")
    public String getArticleUpdPage(String articleId, Model model ,Authentication authentication){
        long l = Long.parseLong(articleId);
        Article article = webArticleService.selArticleByPrimaryKey(l);
        if (article==null){
            return "Redirect:/error";
        }
        if (!((User)authentication.getPrincipal()).getUserId().equals(article.getUserId())){
            System.out.println("用户尝试修改他人文章！");
            return "Redirect:/error";
        }
        model.addAttribute("articleId",article.getArticleId());
        model.addAttribute("articleHeader",article.getArticleHeader());
        model.addAttribute("articleContent",article.getArticleContent());
        model.addAttribute("articlePreview",article.getPreview());
        model.addAttribute("isPublic",article.getIsPublic() == (byte)1 ? "公开":"私密");
        model.addAttribute("ispu",article.getIsPublic());
        return "writeBolg";
    }

    /**
     * 向此接口发送数据来更新文章
     * @param newArticle
     * @param authentication
     * @return
     */
    @PostMapping("/blog/upd-article")
    @ResponseBody
    public RespPojo doUpdArticle(Article newArticle,Authentication authentication){

        if (authentication ==null ||authentication.getPrincipal() == null){
            return RespPojo.failed("您未认证");
        }
        Article article = webArticleService.selArticleByPrimaryKey(newArticle.getArticleId());
        Long userId = ((User) authentication.getPrincipal()).getUserId();
        if (!article.getUserId().equals(userId)){
            System.out.println("登录人id："+userId);
            System.out.println("将要修改的文章id："+article.getUserId());
            return RespPojo.failed("不允许修改他人文章");
        }
        Article the = new Article();
        the.setArticleId(newArticle.getArticleId());
        the.setArticleHeader(newArticle.getArticleHeader());
        the.setArticleContent(newArticle.getArticleContent());
        the.setIsPublic(newArticle.getIsPublic());
        the.setPreview(newArticle.getPreview());
        the.setUpdateTime(new Date());

        int i = webArticleService.updArticleById(the);

        if (i!=1){
            return RespPojo.failed("受影响的行数为:"+i);
        }
        return RespPojo.success();
    }

    @RequestMapping("/protect/img/{imageName}")
    public void  getProtectedImages(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException {

        if (imageName==null||imageName==""){
            response.setContentType("text/json;charset=utf-8");
            RespPojo notfound = RespPojo.exception("您输入的文件名有误");
            ObjectMapper oj = new ObjectMapper();
            String s = oj.writeValueAsString(notfound);
            response.getWriter().write(s);
            return;
        }
        File theProtectedFile = uploadImageService.getTheProtectedFile(imageName);
        if (theProtectedFile == null){
            response.setContentType("text/json;charset=utf-8");
            RespPojo notfound = RespPojo.exception("您请求的资源不存在");
            ObjectMapper oj = new ObjectMapper();
            String s = oj.writeValueAsString(notfound);
            response.getWriter().write(s);
            return;
        }
        response.setContentType("image/jpeg");
        response.setHeader("Cache-Control","max-age="+60*60*24);//缓存24小时，强制缓存
        OutputStream outputStream = response.getOutputStream();
        byte[] bytes = FileUtils.readFileToByteArray(theProtectedFile);
        try{
            outputStream.write(bytes);
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            outputStream.close();
        }
    }

    @RequestMapping("/speCategory")
    @ResponseBody
    public RespPojo getSpeCategoryArticle(Authentication authentication , String categoryId){
        if (categoryId == null || categoryId == ""){
            return RespPojo.exception("类别id有误");
        }
        int isAuth = 1;
        int category = 0;
        if (authentication !=null && authentication.getPrincipal() != null){
            User user = (User)authentication.getPrincipal();
            if (user.getUserId()==1){
                isAuth=0;
            }
        }
        try{
            category = Integer.valueOf(categoryId);
        }catch (Exception e){
            return RespPojo.exception("页码有误");
        }

        List<Article> specifiedCategoryArticleHeader = webArticleService.getSpecifiedCategoryArticleHeader(category, isAuth);
        return RespPojo.success(specifiedCategoryArticleHeader);
    }
}
