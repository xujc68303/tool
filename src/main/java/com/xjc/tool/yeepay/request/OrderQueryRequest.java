package com.xjc.tool.yeepay.request;

import com.xjc.tool.yeepay.utils.DigestUtil;

/**
 * OrderQueryRequest.java
 *
 * @author Xujc
 * @date 2022/5/16
 */
public class OrderQueryRequest {

    /**
     * 业务类型
     */
    private final String p0_Cmd = "QueryOrdDetail";
    /**
     * 商户编号 (商户在易宝支付系统的 唯一身份标识)
     */
    private String p1_MerId;
    /**
     * 商户订单号 (1、若商户填写，则填写的订单号必须在商户的交易中唯一；2、若商户不填写，易宝支付会自动生成随机的商户订单号；3、已付或撤销的订单号，商户不能重复提交。)
     */
    private String p2_Order;
    /**
     * 版本号
     */
    private final String pv_Ver = "3.0";
    /**
     * 查询类型
     */
    private final String p3_ServiceType = "2";

    public String getHmac() {
        String[] strArr = {p0_Cmd, p1_MerId, p2_Order, pv_Ver, p3_ServiceType};
        return DigestUtil.getHmac(strArr, this.getP1_MerId());
    }

    public String getP0_Cmd() {
        return p0_Cmd;
    }

    public String getP1_MerId() {
        return p1_MerId;
    }

    public void setP1_MerId(String p1_MerId) {
        this.p1_MerId = p1_MerId;
    }

    public String getP2_Order() {
        return p2_Order;
    }

    public void setP2_Order(String p2_Order) {
        this.p2_Order = p2_Order;
    }

    public String getPv_Ver() {
        return pv_Ver;
    }

    public String getP3_ServiceType() {
        return p3_ServiceType;
    }
}
