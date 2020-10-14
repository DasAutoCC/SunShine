package com.lkz.blog.web.common;


public class RespPojo {
    private int respCode;
    private String message;
    private Object data;

    /**
     * 请求成功，复用
     * @param respCode
     * @param message
     * @param data
     * @return
     */
    private static RespPojo success(int respCode, String message , Object data){
        RespPojo result = new RespPojo();
        result.setRespCode(respCode);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
    public static RespPojo success(String message,Object data){
        return success(RespCode.REQUEST_SUCCESS , message , data);
    }
    public static RespPojo success(Object data){
        return success(RespCode.REQUEST_SUCCESS, "请求成功", data);
    }
    public static RespPojo success(){
        return success(null);
    }

    /**
     * 请求失败，复用
     * @param respCode
     * @param message
     * @param data
     * @return
     */
    private static RespPojo failed(int respCode, String message,Object data){
        RespPojo result = new RespPojo();
        result.setRespCode(respCode);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
    public static RespPojo failed(String message){
        return failed(RespCode.REQUEST_FAILD , message , null);
    }
    public static RespPojo failed(){
        return failed(RespCode.REQUEST_FAILD, "请求失败", null);
    }

    /**
     * 请求异常，复用
     * @param respCode
     * @param message
     * @param data
     * @return
     */
    private static RespPojo exception(int respCode, String message,Object data){
        RespPojo result = new RespPojo();
        result.setRespCode(respCode);
        result.setMessage(message);
        result.setData(data);
        return result;
    }
    public static RespPojo exception(String message){
        return exception(RespCode.REQUEST_EXCEPTION , message , null);
    }
    public static RespPojo exception(){
        return exception(RespCode.REQUEST_EXCEPTION, "请求异常", null);
    }

    public int getRespCode() {
        return respCode;
    }

    public void setRespCode(int respCode) {
        this.respCode = respCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RespPojo{" +
                "respCode=" + respCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
