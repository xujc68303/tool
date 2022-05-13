package com.xjc.tool.yeepay.response;

import com.xjc.tool.yeepay.api.IVaildHmac;
import com.xjc.tool.yeepay.client.YeePayClient;
import com.xjc.tool.yeepay.exception.YeePayException;
import com.xjc.tool.yeepay.utils.DigestUtil;

public class OrderQueryResponse implements IVaildHmac {

    /**
     * 业务类型 (固定值)
     */
    private String r0_Cmd;
    /**
     * 查询结果 (1：查询正常；50：订单不存在)
     */
    private String r1_Code;
    /**
     * 易宝交易流水号
     */
    private String r2_TrxId;
    /**
     * 支付金额 (单位：元；必须大于等 于0.01)
     */
    private String r3_Amt;
    /**
     * 交易币种 (固定值)
     */
    private String r4_Cur;
    /**
     * 商品名称
     */
    private String r5_Pid;
    /**
     * 商户订单号
     */
    private String r6_Order;
    /**
     * 商户扩展信息
     */
    private String r8_MP;
    /**
     * 退款请求号
     */
    private String rw_RefundRequestID;
    /**
     * 订单创建时间
     */
    private String rx_CreateTime;
    /**
     * 订单成功时间
     */
    private String ry_FinshTime;
    /**
     * 退款请求金额
     */
    private String rz_RefundAmount;
    /**
     * 订单支付状态
     * <ul>
     * <li>INIT：未支付
     * <li>CANCELED：已取消
     * <li>SUCCESS：已支付
     * </ul>
     */
    private String rb_PayStatus;
    /**
     * 已退款次数
     */
    private String rc_RefundCount;
    /**
     * 已退款金额 (单位:元)
     */
    private String rd_RefundAmt;
    /**
     * 安全签名数据
     */
    private String hmac_safe;
    /**
     * 签名数据
     */
    private String hmac;
    /**
     * 错误信息
     */
    private String errorMsg;

    @Override
    public void validateHmac() {
        String[] stringArr = {r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r8_MP,
                rw_RefundRequestID, rx_CreateTime, ry_FinshTime, rz_RefundAmount, rb_PayStatus,
                rc_RefundCount, rd_RefundAmt};
        String localHmac = DigestUtil.getHmac(stringArr, YeePayClient.getMerSecret());
        boolean ishmac_safe = DigestUtil.verifyCallbackHmac_safe(stringArr, hmac_safe, YeePayClient.getMerSecret());

        if (!localHmac.equals(hmac) || !ishmac_safe) {
            throw new YeePayException("验证签名错误", "HMAC_ERROR");
        }
    }

    public String getR0_Cmd() {
        return r0_Cmd;
    }

    public void setR0_Cmd(String r0_Cmd) {
        this.r0_Cmd = r0_Cmd;
    }

    public String getR1_Code() {
        return r1_Code;
    }

    public void setR1_Code(String r1_Code) {
        this.r1_Code = r1_Code;
    }

    public String getR2_TrxId() {
        return r2_TrxId;
    }

    public void setR2_TrxId(String r2_TrxId) {
        this.r2_TrxId = r2_TrxId;
    }

    public String getR3_Amt() {
        return r3_Amt;
    }

    public void setR3_Amt(String r3_Amt) {
        this.r3_Amt = r3_Amt;
    }

    public String getR4_Cur() {
        return r4_Cur;
    }

    public void setR4_Cur(String r4_Cur) {
        this.r4_Cur = r4_Cur;
    }

    public String getR5_Pid() {
        return r5_Pid;
    }

    public void setR5_Pid(String r5_Pid) {
        this.r5_Pid = r5_Pid;
    }

    public String getR6_Order() {
        return r6_Order;
    }

    public void setR6_Order(String r6_Order) {
        this.r6_Order = r6_Order;
    }

    public String getR8_MP() {
        return r8_MP;
    }

    public void setR8_MP(String r8_MP) {
        this.r8_MP = r8_MP;
    }

    public String getRw_RefundRequestID() {
        return rw_RefundRequestID;
    }

    public void setRw_RefundRequestID(String rw_RefundRequestID) {
        this.rw_RefundRequestID = rw_RefundRequestID;
    }

    public String getRx_CreateTime() {
        return rx_CreateTime;
    }

    public void setRx_CreateTime(String rx_CreateTime) {
        this.rx_CreateTime = rx_CreateTime;
    }

    public String getRy_FinshTime() {
        return ry_FinshTime;
    }

    public void setRy_FinshTime(String ry_FinshTime) {
        this.ry_FinshTime = ry_FinshTime;
    }

    public String getRz_RefundAmount() {
        return rz_RefundAmount;
    }

    public void setRz_RefundAmount(String rz_RefundAmount) {
        this.rz_RefundAmount = rz_RefundAmount;
    }

    public String getRb_PayStatus() {
        return rb_PayStatus;
    }

    public void setRb_PayStatus(String rb_PayStatus) {
        this.rb_PayStatus = rb_PayStatus;
    }

    public String getRc_RefundCount() {
        return rc_RefundCount;
    }

    public void setRc_RefundCount(String rc_RefundCount) {
        this.rc_RefundCount = rc_RefundCount;
    }

    public String getRd_RefundAmt() {
        return rd_RefundAmt;
    }

    public void setRd_RefundAmt(String rd_RefundAmt) {
        this.rd_RefundAmt = rd_RefundAmt;
    }

    public String getHmac_safe() {
        return hmac_safe;
    }

    public void setHmac_safe(String hmac_safe) {
        this.hmac_safe = hmac_safe;
    }

    public String getHmac() {
        return hmac;
    }

    public void setHmac(String hmac) {
        this.hmac = hmac;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
