package com.lkz.blog.web.controller;

import com.lkz.blog.web.common.RespPojo;
import com.lkz.blog.web.utils.BlogStringUtil;
import com.lkz.blog.web.utils.Gen;
import com.lkz.blog.web.utils.PathUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PageController {

    private String[] welcome = {"海内存知己,天涯若比邻","生活原本沉闷，但跑起来就有风","世界很大，至少比我想象中的要大","产品经理与程序员之间的矛盾是不可能避免的么","我们都需要专业的人来做专业的事"};
    private String[] headerMsg = {"世界很大，风景很美","满月照人间","我要我的雕刻刀","叶绿素夹心糖","假如你是阳光","放弃和失败都很容易"};
    private Map<String,String> timeMsg;

    @RequestMapping("/")
    public String indexPage(){
        return "redirect:/index.html";
    }

    @ResponseBody
    @RequestMapping("/welcome")
    public RespPojo welcomeMsg(){


        Date now = new Date();
        int hours = now.getHours();
        String welcomeIns =welcome[Gen.getNum(0,welcome.length-1)];
        String headerIns = headerMsg[Gen.getNum(0,headerMsg.length-1)];
        String timeMsgIns = "";
        if (hours>=0 && hours<5){
            timeMsgIns =  timeMsg.get("凌晨");
        }
        if (hours>=5 && hours<10){
            timeMsgIns =  timeMsg.get("早上");
        }
        if (hours>=10 && hours<17){
            timeMsgIns =  timeMsg.get("中午");
        }
        if (hours>=17 && hours<19){
            timeMsgIns =  timeMsg.get("下午");
        }
        if (hours>=19 && hours<21){
            timeMsgIns =  timeMsg.get("晚上");
        }
        if (hours>=21 && hours<24){
            timeMsgIns =  timeMsg.get("深夜");
        }
        Map result = new HashMap();
        result.put("welcome",welcomeIns);
        result.put("headerMsg",headerIns);
        result.put("timeMsg",timeMsgIns);
        return RespPojo.success(result);
    }

    public PageController() {
        this.timeMsg = new HashMap();
        this.timeMsg.put("凌晨","身体才是革命的本钱");//0-5
        this.timeMsg.put("早上","早上好!准备开启美好的一天");//5-10
        this.timeMsg.put("中午","今天有没有偷懒摸鱼？");//10-17
        this.timeMsg.put("下午","得好好想想晚餐吃什么");//17-19
        this.timeMsg.put("晚上","洗个澡!准备睡个好觉");//19-21
        this.timeMsg.put("深夜","熬夜伤头发啊");//21-24
    }
}
