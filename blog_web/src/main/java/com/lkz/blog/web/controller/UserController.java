package com.lkz.blog.web.controller;

import com.lkz.blog.pojo.User;
import com.lkz.blog.web.common.RespPojo;
import com.lkz.blog.web.config.VerificationCodeFilter;
import com.lkz.blog.web.utils.BlogStringUtil;
import com.lkz.blog.web.utils.ImageUtil;
import com.lkz.blog.web.webService.WebUserService;
import com.lkz.blog.web.webService.impl.CustomerMailSenderService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;


@Controller
public class UserController {

    @Resource
    private WebUserService userService;

    @Resource
    private CustomerMailSenderService mailSendService;

    @Resource
    private ExecutorService executorService;


    @RequestMapping("/isLogin")
    @ResponseBody
    public RespPojo isLogin(Authentication securityAuth){

        //未登录的securityAuth为null
        if (securityAuth == null || securityAuth.getPrincipal() == null){
            return RespPojo.exception("您未登录");
        }

        User principal = (User) securityAuth.getPrincipal();
        Map<String,String> resMap = new HashMap<>();
        resMap.put("userName",principal.getNickName());
        resMap.put("userId",principal.getUserId()+"");
        resMap.put("declaration",principal.getDeclaration());
        resMap.put("userHeader",principal.getUserHead());
        return RespPojo.success("您已登录",resMap);
    }

    @RequestMapping("/reg")
    @ResponseBody
    public RespPojo setUserHeaderImage(User user,String password,HttpServletRequest request,String verifCode){
        HttpSession session = request.getSession(false);
        if (session == null){
            return RespPojo.exception("当前验证码已过期，请重新获取");
        }
        Object sessionVerifCode = session.getAttribute("verifCode");
        if (sessionVerifCode == null){
            return RespPojo.exception("请重新获取验证码后重试");
        }
        if (verifCode==null){
            return RespPojo.exception("当前验证码已过期，请重新获取2");
        }
        if (verifCode==null||verifCode==""){
            return RespPojo.exception("请输入验证码");
        }
        String s1 = sessionVerifCode.toString();
        if (!s1.equals(verifCode)){
            return RespPojo.exception("您输入的验证码不匹配");
        }
        String s = checkUser(user);
        if (s!=null){
            return RespPojo.exception(s);
        }
        if (password==null){
            return RespPojo.exception("密码不能为空");
        }
        if (password.length()<6){
            return RespPojo.exception("密码太短");
        }
        if (password.length()>24){
            return RespPojo.exception("密码怼这么长？");
        }
        boolean passwordSpecialChar = BlogStringUtil.isSpecialChar(password);
        if(passwordSpecialChar){
            return RespPojo.exception("您的密码包含特殊字符");
        }
        com.lkz.blog.pojo.Authentication userAuth = new com.lkz.blog.pojo.Authentication();
        userAuth.setAuthenticationContent(password);

        int i = userService.insertUserAndSignIn(user,userAuth);

        if (i!=1){
            return RespPojo.failed("内部错误");
        }
        //异步进行发送邮件通知我自己
        executorService.execute(() -> mailSendService.sendCustomerMsg("1321200962@qq.com","有新用户注册成功,用户名:"+user.getNickName()));

        return RespPojo.success();
    }

    /**
     * 获取一个验证码，写好了暂时没用了
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping("/get-code")
    @ResponseBody
    public void getVerificationCode (HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        ServletOutputStream outputStream = response.getOutputStream();
        Map<String,Object> map = ImageUtil.generateCodeAndPic();
        HttpSession session = request.getSession();
        //将画的字符串转换为小写字母存入session中
        String code = map.get("code").toString().toLowerCase();
        session.setAttribute(VerificationCodeFilter.VERIFICATION_CODE,code);
        ImageIO.write((RenderedImage) map.get("codePic"), "jpeg", outputStream);
    }

    private String checkUser(User user){
        if (user==null){
            return "主体为null";
        }
        String username = user.getUsername();
        String email = user.getEmail();
        if (username==null ||username==""||email==null||email==""){
            return "信息不完整";
        }
        boolean b = BlogStringUtil.emailFormat(email);
        if (!b){
            return "email格式错误";
        }
        boolean specialChar = BlogStringUtil.isSpecialChar(username);
        if (username.length()>15){
            return "您的昵称过长";
        }
        if (specialChar){
            return "您的昵称中包含特殊字符";
        }
        int i = userService.selectCountByNickName(username);
        if (i == 1) {
            return "此昵称已被注册";
        }
        return null;
    }

    @RequestMapping("/count-user")
    @ResponseBody
    public RespPojo selectCountUserByNickName(String username){
        boolean specialChar = BlogStringUtil.isSpecialChar(username);
        if (username.length()>10){
            return RespPojo.exception("您的昵称过长");
        }
        if (specialChar){
            return RespPojo.exception("您的昵称中包含特殊字符");
        }
        int i = userService.selectCountByNickName(username);
        if (i >= 1) {
            return RespPojo.exception("此昵称以被注册");
        }
        return RespPojo.success("此昵称可以使用",null);
    }

    /**
     * 此控制器已添加到重复提交过滤器的保护中，一分钟只能访问一次
     * @param mailAddress
     * @param request
     * @return
     */
    @RequestMapping("/send-verif-code")
    @ResponseBody
    public RespPojo sendVerifCode(String mailAddress,HttpServletRequest request){
        if (mailAddress==null){
            //发送失败可以再次请求发送而不会被重复提交拦截器拦截
            HttpSession session = request.getSession(false);
            session.removeAttribute(request.getRequestURL().toString());
            return RespPojo.exception("请输入邮箱再试！");
        }

        boolean b = BlogStringUtil.emailFormat(mailAddress);
        if (!b){
            HttpSession session = request.getSession(false);
            session.removeAttribute(request.getRequestURL().toString());
            return RespPojo.exception("您的邮箱格式有误！");
        }
        //生成4位的数字发送到对方邮箱中
        Random random = new Random();
        StringBuilder verifCode = new StringBuilder();
        for (int i = 0 ;i<4 ;i++){
            int k = random.nextInt();
            int j = Math.abs(k % 10);
            verifCode.append(j);
        }
        String s = mailSendService.sendVerifMsg(mailAddress, verifCode.toString());
        if (s!=""){
            HttpSession session = request.getSession(false);
            session.removeAttribute(request.getRequestURL().toString());
            return RespPojo.exception(s);
        }
        //验证码放入httpSession中，用来验证
        HttpSession session = request.getSession();
        session.setAttribute("verifCode",verifCode.toString());
        return RespPojo.success("发送成功",null);
    }

    @RequestMapping("/count-email")
    @ResponseBody
    public RespPojo selectCountByEmail(String email){
        if (!BlogStringUtil.emailFormat(email)){
            return RespPojo.exception("您的邮箱格式有误！");
        }
        int i = userService.selectCountByEmail(email);
        if (i>=1){
            return RespPojo.exception("此邮箱已被注册！");
        }
        return RespPojo.success("此邮箱未注册",null);
    }

    //登录接口，利用了请求转发策略
    @RequestMapping("/login")
    public String loginPage(HttpServletRequest request, Model model){
        if (request.getAttribute("errorMsg")!=null){
            model.addAttribute("errorMsg","账号或密码错误");
            return "login";
        }
        return "login";
    }

}
