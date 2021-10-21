package com.xjc.tool.result;

/**
 * BaseException.java
 *
 * @author Xujc
 * @date 2021/10/21
 */
public abstract class BaseException extends RuntimeException {
    private static final long  serialVersionUID = 1L;

    private int errorCode;

    public BaseException(String errorMessage){
        super(errorMessage);
    }

    public BaseException(int errorCode,String errorMessage){
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public BaseException(String errorMessage,Throwable e){
        super(errorMessage,e);
    }

    public BaseException(int errorCode,String errorMessage,Throwable e){
        super(errorMessage,e);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
