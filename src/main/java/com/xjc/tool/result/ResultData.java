package com.xjc.tool.result;

import com.alibaba.fastjson.JSONObject;

/**
 * ResultData.java
 *
 * @author Xujc
 * @date 2021/10/21
 */
public class ResultData<T> {

    private int status;

    private String message;

    private T data;

    private long timestamp = System.currentTimeMillis();

    public ResultData() {

    }

    public static <T> ResultData<T> success() {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(ReturnCode.RC200.getCode());
        resultData.setMessage(ReturnCode.RC200.getMessage());
        return resultData;
    }

    public static <T> ResultData<T> success(T data) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(ReturnCode.RC200.getCode());
        resultData.setMessage(ReturnCode.RC200.getMessage());
        resultData.setData(data);
        return resultData;
    }

    public static <T> ResultData<T> fail() {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(ReturnCode.RC500.getCode());
        resultData.setMessage(ReturnCode.RC500.getMessage());
        return resultData;
    }

    public static <T> ResultData<T> fail(ReturnCode returnCode) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(returnCode.getCode());
        resultData.setMessage(returnCode.getMessage());
        return resultData;
    }

    public static <T> ResultData<T> fail(int code, String message) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setStatus(code);
        resultData.setMessage(message);
        return resultData;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
