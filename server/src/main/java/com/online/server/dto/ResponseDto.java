package com.online.server.dto;

/**
 * 响应数据传输
 * @author MrDong
 */
public class ResponseDto<T> {

    /**
     * 业务上的成功或失败
     */
    private boolean success = true;

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回泛型数据，自定义类型
     */
    private T data;
    public ResponseDto() {
    }
    public ResponseDto(boolean success, int code, String msg, T data) {
        this.success = success;
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResponseDto ok(int code,String msg){
        return new ResponseDto(true,code,msg,null);
    }
    public ResponseDto ok(int code, String msg, T data){
        return new ResponseDto(true,code,msg,data);
    }
    public static ResponseDto fail(int code, String msg){
        return new ResponseDto(false,code,msg,null);
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ResponseDto{");
        sb.append("success=").append(success);
        sb.append(", code='").append(code).append('\'');
        sb.append(", msg='").append(msg).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
