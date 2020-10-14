package com.lkz.blog.web.exception;

import com.lkz.blog.web.common.RespCode;

/**
 * RestController入参不合法抛出此异常
 * 注意：此异常须是被restController中的方法抛出，
 *      否则异常处理可能会返回不正确的数据类型
 */
public class ArgumentException extends RuntimeException {
    private int errorCode;

    private String errorMessage;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ArgumentException(){
    }

    public ArgumentException(int errorCode , String errorMessage ,Throwable cause){
        super(errorMessage,cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ArgumentException(String errorMessage){
        this(RespCode.REQUEST_EXCEPTION,errorMessage,null);
    }
    public ArgumentException(String errorMessage , Throwable cause){
        this(RespCode.REQUEST_EXCEPTION,errorMessage,cause);
    }


}
