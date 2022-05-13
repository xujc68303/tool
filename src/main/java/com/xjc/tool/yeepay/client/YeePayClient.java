package com.xjc.tool.yeepay.client;

import com.xjc.tool.yeepay.api.IVaildHmac;
import com.xjc.tool.yeepay.api.IYeePay;
import com.xjc.tool.yeepay.api.PayApi;
import com.xjc.tool.yeepay.constant.YeePayConstant;
import com.xjc.tool.yeepay.converter.ConverterFactory;
import com.xjc.tool.yeepay.exception.YeePayException;
import com.xjc.tool.yeepay.request.OrderCancelRequest;
import com.xjc.tool.yeepay.request.OrderQueryRequest;
import com.xjc.tool.yeepay.request.PayRequest;
import com.xjc.tool.yeepay.response.OrderCancelResponse;
import com.xjc.tool.yeepay.response.OrderQueryResponse;
import com.xjc.tool.yeepay.utils.DigestUtil;
import com.xjc.tool.yeepay.utils.QueryFormUtils;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Map;

/**
 * YeePayClient.java
 *
 * @author Xujc
 * @date 2022/5/16
 */
public class YeePayClient implements IYeePay {

    /**
     * 商户编号
     */
    private static String merId;
    /**
     * 商户秘钥
     */
    private static String merSecret;

    private final PayApi payApi;

    public YeePayClient() {
        this(new OkHttpClient());
    }

    public YeePayClient(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(YeePayConstant.BASE_REQUEST)
                .addConverterFactory(new ConverterFactory())
                .client(client).build();
        this.payApi = retrofit.create(PayApi.class);
    }

    public static String getMerId() {
        return merId;
    }

    public static void setMerId(String merId) {
        YeePayClient.merId = merId;
    }

    public static String getMerSecret() {
        return merSecret;
    }

    public static void setMerSecret(String merSecret) {
        YeePayClient.merSecret = merSecret;
    }

    @Override
    public String getPayURL(PayRequest request) {
        return payApi.unifiedOrder(QueryFormUtils.getEncodedQueryParams(request)).request().url().toString();
    }

    @Override
    public OrderQueryResponse queryOrder(OrderQueryRequest request) {
        return execute(payApi.queryOrder(request));
    }

    @Override
    public OrderCancelResponse cancelOrder(OrderCancelRequest request) {
        return execute(payApi.cancelOrder(request));
    }

    @Override
    public void validateCallback(Map<String, String> request) {
        String p1_MerId = request.get("p1_MerId");
        String r0_Cmd = request.get("r0_Cmd");
        String r1_Code = request.get("r1_Code");
        String r2_TrxId = request.get("r2_TrxId");
        String r3_Amt = request.get("r3_Amt");
        String r4_Cur = request.get("r4_Cur");
        String r5_Pid = request.get("r5_Pid");
        String r6_Order = request.get("r6_Order");
        String r7_Uid = request.get("r7_Uid");
        String r8_MP = request.get("r8_MP");
        String r9_BType = request.get("r9_BType");
        String hmac = request.get("hmac");
        String hmac_safe = request.get("hmac_safe");

        if(hmac==null||"".equals(hmac)){
            throw new YeePayException("验证签名错误", "HMAC_ERROR");
        }

        String[] strArr = {p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType};

        boolean hmacIsCorrect = hmac.equals(DigestUtil.getHmac(strArr, YeePayClient.getMerSecret()));
        boolean hmacsafeIsCorrect = DigestUtil.verifyCallbackHmac_safe(strArr, hmac_safe, YeePayClient.getMerSecret());

        if (!hmacIsCorrect || !hmacsafeIsCorrect) {
            throw new YeePayException("验证签名错误", "HMAC_ERROR");
        }
    }

    private <T> T execute(Call<T> post) {
        try {
            Response<T> execute = post.execute();
            T body = execute.body();
            if(body instanceof IVaildHmac){
                ((IVaildHmac) body).validateHmac();
            }
            return body;
        } catch (IOException e) {
            throw new YeePayException("请求失败", "EXECUTE_ERROR");
        }
    }
}
