package com.xjc.tool.yeepay.exception;

/**
 * YeePayException.java
 *
 * @author Xujc
 * @date 2022/5/16
 */
public class YeePayException extends RuntimeException {

    private final String errCode;

    public YeePayException(String errCode) {
        this.errCode = errCode;
    }

    public YeePayException(String message, String errCode) {
        super(message);
        this.errCode = errCode;
    }

    public YeePayException(String message, Throwable cause, String errCode) {
        super(message, cause);
        this.errCode = errCode;
    }

    public YeePayException(Throwable cause, String errCode) {
        super(cause);
        this.errCode = errCode;
    }

    public YeePayException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String errCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errCode = errCode;
    }

    public String getErrCode() {
        return errCode;
    }
}
