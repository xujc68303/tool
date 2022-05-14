package com.xjc.tool.yeepay.config;

public class YeePayConfig {

    /**
     * 商户编号
     */
    private String merId;
    /**
     * 商户秘钥
     */
    private String merSecret;

    public YeePayConfig() {
    }

    public YeePayConfig(String merId, String merSecret) {
        this.merId = merId;
        this.merSecret = merSecret;
    }

    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public String getMerSecret() {
        return merSecret;
    }

    public void setMerSecret(String merSecret) {
        this.merSecret = merSecret;
    }
}
