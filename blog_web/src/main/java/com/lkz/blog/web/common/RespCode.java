package com.lkz.blog.web.common;

/**
 * 定义异常类型，目前只代表基础响应码。具体对应的信息应该在返回的json数据中添加具体描述
 */
public class RespCode {
    /**
     * 请求成功
     */
    public static final int REQUEST_SUCCESS = 200;
    /**
     * 请求异常
     */
    public static final int REQUEST_EXCEPTION = 400;
    /**
     * 请求错误
     */
    public static final int REQUEST_FAILD = 500;
}
