package com.xjc.tool.yeepay.response;


import com.xjc.tool.yeepay.api.IVaildHmac;
import com.xjc.tool.yeepay.client.YeePayClient;
import com.xjc.tool.yeepay.exception.YeePayException;
import com.xjc.tool.yeepay.utils.DigestUtil;

public class OrderCancelResponse implements IVaildHmac {

    /**
     * 业务类型
     */
    private String r0_Cmd;
    /**
     * 撤销结果
     * <li></li>1： 撤销成功
     * <li></li>53：订单已经成功，不可撤销。
     */
    private String r1_Code;
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
        String[] stringArr = {r0_Cmd, r1_Code};
        String localHmac = DigestUtil.getHmac(stringArr, YeePayClient.getMerSecret());
        if (!localHmac.equals(hmac) || !DigestUtil.verifyCallbackHmac_safe(stringArr, hmac_safe, YeePayClient.getMerSecret())) {
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
