package com.xjc.tool.yeepay.request;

import com.xjc.tool.yeepay.client.YeePayClient;
import com.xjc.tool.yeepay.utils.DigestUtil;

/**
 * PayRequest.java
 *
 * @author Xujc
 * @date 2022/5/16
 */
public class PayRequest {

    /**
     * 业务类型
     */
    private final String p0_Cmd = "Buy";
    /**
     * 商户编号 (商户在易宝支付系统的 唯一身份标识)
     */
    private String p1_MerId = YeePayClient.getMerId();
    /**
     * 商户订单号 (1、若商户填写，则填写的订单号必须在商户的交易中唯一；2、若商户不填写，易宝支付会自动生成随机的商户订单号；3、已付或撤销的订单号，商户不能重复提交。)
     */
    private String p2_Order;
    /**
     * 支付金额 (单位：元；必须大于等 于0.01)
     */
    private String p3_Amt;
    /**
     * 交易币种
     */
    private final String p4_Cur = "CNY";
    /**
     * 商品名称 (1、若为中文，请注意转 码：GBK 或GB2312。 2、商品名称如果为空， 默认显示「商品名称」 四个汉字。 3、当支付方式为网银一 键时此参数必传。)
     */
    private String p5_Pid;
    /**
     * 商品种类 (若为中文，请注意转 码：GBK 或GB2312。)
     */
    private String p6_Pcat;
    /**
     * 商品描述 (若为中文，请注意转 码：GBK 或GB2312。)
     */
    private String p7_Pdesc;
    /**
     * 回调地址
     */
    private String p8_Url;
    /**
     * 送货地址 (1 - 需要用户将送货地址 留在易宝支付系统；0 - 10 否 易宝支付有限公司——网银标准版开发手册第9 / 24页 9 不需要。 默认值为：0)
     */
    private String p9_SAF;
    /**
     * 商户扩展信息 (若为中文，请注意转 码：GBK 或GB2312。)
     */
    private String pa_MP;
    /**
     * 支付通道编号 (1、若不填写，则直接跳 转到易宝支付的默认支 付网关。 2、若填写，则直接跳到 对应的银行支付页面。)
     * <p>B2C通道:</p>
     * <ul>
     * <li>ICBC-NET-B2C 工商银行
     * <li>CMBCHINA-NET-B2C 招商银行
     * <li>CCB-NET-B2C 建设银行
     * <li>BOCO-NET-B2C 交通银行[借]
     * <li>CIB-NET-B2C 兴业银行
     * <li>CMBC-NET-B2C 中国民生银行
     * <li>CEB-NET-B2C 光大银行
     * <li>BOC-NET-B2C 中国银行
     * <li>PINGANBANK-NET-B2C 平安银行
     * <li>ECITIC-NET-B2C 中信银行
     * <li>SDB-NET-B2C 深圳发展银行
     * <li>GDB-NET-B2C 广发银行
     * <li>SHB-NET-B2C 上海银行
     * <li>SPDB-NET-B2C 上海浦东发展银行
     * <li>HXB-NET-B2C 华夏银行「借」
     * <li>BCCB-NET-B2C 北京银行
     * <li>ABC-NET-B2C 中国农业银行
     * <li>POST-NET-B2C 中国邮政储蓄银行「借」
     * <li>BJRCB-NET-B2C 北京农村商业银行「借」-暂不可用
     * </ul>
     * <p>B2B通道:</p>
     * <ul>
     * <li>ICBC-NET-B2B 工商银行
     * <li>CMBCHINA-NET-B2B 招商银行
     * <li>ABC-NET-B2B 中国农业银行
     * <li>CCB-NET-B2B 建设银行
     * <li>CEB-NET-B2B 光大银行
     * <li>BOC-NET-B2B 中国银行
     * <li>SDB-NET-B2B 平安银行
     * <li>SPDB-NET-B2B 上海浦东发展银行
     * <li>CMBC-NET-B2B 民生银行
     * <li>SDB-NET-B2B 深圳发展银行
     * <li>BOCO-NET-B2B 交通银行
     * <li>HXB-NET-B2B 华夏银行
     * <li>BCCB-NET-B2B 北京银行
     * <li>ECITIC-NET-B2B 中信银行
     * <li>CIB-NET-B2B 兴业银行
     * <li>GDB-NET-B2B 广发银行
     * </ul>
     * <p>网银一键支付通道:</p>
     * <ul>
     * <li>YJZF-NET-B2C 网银一键支付通道
     * </ul>
     * <p>会员支付:</p>
     * <ul>
     * <li>1000000-NET 会员支付
     * </ul>
     */
    private String pd_FrpId;
    /**
     * 订单有效期 (默认值:7)
     */
    private String pm_Period;
    /**
     * 订单有效期单位 (订单有效期的单位：year、month、day、hour、minute 、second 否则此参数将无效。默认值：day)
     */
    private String pn_Unit;
    /**
     * 应答机制
     */
    private final String pr_NeedResponse = "1";
    /**
     * 考生/用户姓名 (当支付方式为网银一键时指用户姓名)
     */
    private String pt_UserName;
    /**
     * 身份证号 (当支付方式为网银一键时指用户身份证号)
     */
    private String pt_PostalCode;
    /**
     * 地区 (地区，注意中文转码)
     */
    private String pt_Address;
    /**
     * 报考序号/银行卡号 (当支付方式为网银一键时指用户的银行卡号)
     */
    private String pt_TeleNo;
    /**
     * 手机号 (当支付方式为网银一键时指用户预留银行手机号)
     */
    private String pt_Mobile;
    /**
     * 邮件地址
     */
    private String pt_Email;
    /**
     * 用户标志 (用户唯一标识ID，绑卡支付时必传)
     */
    private String pt_LeaveMessage;

    public String getHmac() {
        String[] strArr = new String[]{p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
                p8_Url, p9_SAF, pa_MP, pd_FrpId, pm_Period, pn_Unit, pr_NeedResponse,
                pt_UserName, pt_PostalCode, pt_Address, pt_TeleNo, pt_Mobile, pt_Email, pt_LeaveMessage};
        return DigestUtil.getHmac(strArr, YeePayClient.getMerSecret());
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

    public String getP3_Amt() {
        return p3_Amt;
    }

    public void setP3_Amt(String p3_Amt) {
        this.p3_Amt = p3_Amt;
    }

    public String getP4_Cur() {
        return p4_Cur;
    }

    public String getP5_Pid() {
        return p5_Pid;
    }

    public void setP5_Pid(String p5_Pid) {
        this.p5_Pid = p5_Pid;
    }

    public String getP6_Pcat() {
        return p6_Pcat;
    }

    public void setP6_Pcat(String p6_Pcat) {
        this.p6_Pcat = p6_Pcat;
    }

    public String getP7_Pdesc() {
        return p7_Pdesc;
    }

    public void setP7_Pdesc(String p7_Pdesc) {
        this.p7_Pdesc = p7_Pdesc;
    }

    public String getP8_Url() {
        return p8_Url;
    }

    public void setP8_Url(String p8_Url) {
        this.p8_Url = p8_Url;
    }

    public String getP9_SAF() {
        return p9_SAF;
    }

    public void setP9_SAF(String p9_SAF) {
        this.p9_SAF = p9_SAF;
    }

    public String getPa_MP() {
        return pa_MP;
    }

    public void setPa_MP(String pa_MP) {
        this.pa_MP = pa_MP;
    }

    public String getPd_FrpId() {
        return pd_FrpId;
    }

    public void setPd_FrpId(String pd_FrpId) {
        this.pd_FrpId = pd_FrpId;
    }

    public String getPm_Period() {
        return pm_Period;
    }

    public void setPm_Period(String pm_Period) {
        this.pm_Period = pm_Period;
    }

    public String getPn_Unit() {
        return pn_Unit;
    }

    public void setPn_Unit(String pn_Unit) {
        this.pn_Unit = pn_Unit;
    }

    public String getPr_NeedResponse() {
        return pr_NeedResponse;
    }

    public String getPt_UserName() {
        return pt_UserName;
    }

    public void setPt_UserName(String pt_UserName) {
        this.pt_UserName = pt_UserName;
    }

    public String getPt_PostalCode() {
        return pt_PostalCode;
    }

    public void setPt_PostalCode(String pt_PostalCode) {
        this.pt_PostalCode = pt_PostalCode;
    }

    public String getPt_Address() {
        return pt_Address;
    }

    public void setPt_Address(String pt_Address) {
        this.pt_Address = pt_Address;
    }

    public String getPt_TeleNo() {
        return pt_TeleNo;
    }

    public void setPt_TeleNo(String pt_TeleNo) {
        this.pt_TeleNo = pt_TeleNo;
    }

    public String getPt_Mobile() {
        return pt_Mobile;
    }

    public void setPt_Mobile(String pt_Mobile) {
        this.pt_Mobile = pt_Mobile;
    }

    public String getPt_Email() {
        return pt_Email;
    }

    public void setPt_Email(String pt_Email) {
        this.pt_Email = pt_Email;
    }

    public String getPt_LeaveMessage() {
        return pt_LeaveMessage;
    }

    public void setPt_LeaveMessage(String pt_LeaveMessage) {
        this.pt_LeaveMessage = pt_LeaveMessage;
    }
}
