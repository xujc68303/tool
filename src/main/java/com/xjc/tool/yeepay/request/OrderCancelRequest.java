package com.xjc.tool.yeepay.request;

import com.xjc.tool.yeepay.utils.DigestUtil;

/**
 * OrderCancelRequest.java
 *
 * @author Xujc
 * @date 2022/5/16
 */
public class OrderCancelRequest {

    /**
     * 业务类型 (固定值)
     */
    private final String p0_Cmd = "CancelOrd";
    /**
     * 商户编号 (商户在易宝支付系统的 唯一身份标识)
     */
    private String p1_MerId;
    /**
     * 商户订单号 (原订单的商户订单号)
     */
    private String pb_TrxId;
    /**
     * 版本号
     */
    private final String pv_Ver = "1";
    /**
     * 签名结果
     *
     * @return
     */
    public String getHmac() {
        String[] strArr = {p0_Cmd, p1_MerId, pb_TrxId, pv_Ver};
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

    public String getPb_TrxId() {
        return pb_TrxId;
    }

    public void setPb_TrxId(String pb_TrxId) {
        this.pb_TrxId = pb_TrxId;
    }

    public String getPv_Ver() {
        return pv_Ver;
    }
}
