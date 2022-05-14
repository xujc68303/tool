package com.xjc.tool.yeepay.engine;

import com.xjc.tool.yeepay.config.YeePayConfig;
import com.xjc.tool.yeepay.constant.YeePayConstant;
import com.xjc.tool.yeepay.converter.ConverterFactory;
import com.xjc.tool.yeepay.exception.YeePayException;
import com.xjc.tool.yeepay.request.OrderCancelRequest;
import com.xjc.tool.yeepay.request.OrderQueryRequest;
import com.xjc.tool.yeepay.request.PayRequest;
import com.xjc.tool.yeepay.response.CancelOrderResponse;
import com.xjc.tool.yeepay.response.QueryOrderResponse;
import com.xjc.tool.yeepay.utils.DigestUtil;
import com.xjc.tool.yeepay.utils.QueryFormUtils;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * YeePayClient.java
 *
 * @author Xujc
 * @date 2022/5/16
 */
public class YeePayEngine implements IYeePay {

    /**
     * 商户编号
     */
    private final String merId;
    /**
     * 商户秘钥
     */
    private final String merSecret;

    private final PayApi payApi;

    public YeePayEngine(YeePayConfig yeePayConfig) {
        this.merId = yeePayConfig.getMerId();
        this.merSecret = yeePayConfig.getMerSecret();
        this.payApi = new Retrofit.Builder()
                .baseUrl(YeePayConstant.BASE_REQUEST)
                .addConverterFactory(new ConverterFactory())
                .client(new OkHttpClient.Builder()
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES))
                        .pingInterval(30, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .build().create(PayApi.class);
    }

    public String getMerId() {
        return merId;
    }

    public String getMerSecret() {
        return merSecret;
    }

    public PayApi getPayApi() {
        return payApi;
    }

    @Override
    public String getPayURL(PayRequest request) {
        return payApi.unifiedOrder(QueryFormUtils.getEncodedQueryParams(request)).request().url().toString();
    }

    @Override
    public QueryOrderResponse queryOrder(OrderQueryRequest request) {
        return execute(payApi.queryOrder(request));
    }

    @Override
    public CancelOrderResponse cancelOrder(OrderCancelRequest request) {
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
        if (hmac == null || "".equals(hmac)) {
            throw new YeePayException("验证签名错误", "HMAC_ERROR");
        }
        String[] strArr = {p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType};
        boolean hmacIsCorrect = hmac.equals(DigestUtil.getHmac(strArr, this.getMerSecret()));
        boolean hmacsafeIsCorrect = DigestUtil.verifyCallbackHmac_safe(strArr, hmac_safe, this.getMerSecret());
        if (!hmacIsCorrect || !hmacsafeIsCorrect) {
            throw new YeePayException("验证签名错误", "HMAC_ERROR");
        }
    }

    private <T> T execute(Call<T> post) {
        try {
            Response<T> execute = post.execute();
            T body = execute.body();
            if (body instanceof IVaildHmac) {
                ((IVaildHmac) body).validateHmac();
            }
            return body;
        } catch (IOException e) {
            throw new YeePayException("请求失败", "execute_error");
        }
    }
}
