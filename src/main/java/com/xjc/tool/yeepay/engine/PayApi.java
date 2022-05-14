package com.xjc.tool.yeepay.engine;

import com.xjc.tool.yeepay.request.OrderCancelRequest;
import com.xjc.tool.yeepay.request.OrderQueryRequest;
import com.xjc.tool.yeepay.response.CancelOrderResponse;
import com.xjc.tool.yeepay.response.QueryOrderResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

import java.util.Map;

/**
 * PayApi.java
 *
 * @author Xujc
 * @date 2022/5/16
 */
public interface PayApi {

    @GET("https://www.yeepay.com/app-merchant-proxy/node")
    Call<Void> unifiedOrder(@QueryMap(encoded = true) Map<String, String> params);

    @POST("https://cha.yeepay.com/app-merchant-proxy/command")
    Call<QueryOrderResponse> queryOrder(@Body OrderQueryRequest request);

    @POST("https://cha.yeepay.com/app-merchant-proxy/command")
    Call<CancelOrderResponse> cancelOrder(@Body OrderCancelRequest request);
}
