package com.log.log.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.Assert;

import java.io.Serializable;

public final class ResultVO<T> implements Serializable {

    public static Integer CODE_SUCCESS = 200;

    /**
     * 状态码
     */
    private Integer status;
    /**
     * 错误提示
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

    /**
     * 将传入的 result 对象，转换成另外一个泛型结果的对象
     *
     * 因为 A 方法返回的 CommonResult 对象，不满足调用其的 B 方法的返回，所以需要进行转换。
     *
     * @param result 传入的 result 对象
     * @param <T> 返回的泛型
     * @return 新的 CommonResult 对象
     */
    public static <T> ResultVO<T> error(ResultVO<?> result) {
        return error(result.getStatus(), result.getMsg());
    }

    public static ResultVO error(){
        return error(500, "未知异常，请联系管理员");
    }

    public static ResultVO error(String msg){
        return error(500, msg);
    }


    public static <T> ResultVO<T> error(Integer code, String message) {
        Assert.isTrue(!CODE_SUCCESS.equals(code), "code 必须是错误的！");
        ResultVO<T> result = new ResultVO<>();
        result.status = code;
        result.msg = message;
        return result;
    }

    /**
     * 成功，返回数据
     * @param data 返回数据
     * @param <T>
     * @return
     */
    public static <T> ResultVO<T> success(T data) {
        ResultVO<T> result = new ResultVO<>();
        result.status = CODE_SUCCESS;
        result.data = data;
        result.msg = "";
        return result;
    }

    /**
     * 成功，返回数据和 提示信息
     * @param data  返回数据
     * @param msg  提示信息
     * @param <T>
     * @return
     */
    public static <T> ResultVO<T> success(T data, String msg) {
        ResultVO<T> result = new ResultVO<>();
        result.status = CODE_SUCCESS;
        result.data = data;
        result.msg = msg;
        return result;
    }

    public static ResultVO success(){
        ResultVO result = new ResultVO<>();
        result.status = CODE_SUCCESS;
        result.data = null;
        result.msg = "操作成功";
        return result;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @JsonIgnore
    public boolean isSuccess() {
        return CODE_SUCCESS.equals(status);
    }

    @JsonIgnore
    public boolean isError() {
        return !isSuccess();
    }

    @Override
    public String toString() {
        return "CommonResult{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}