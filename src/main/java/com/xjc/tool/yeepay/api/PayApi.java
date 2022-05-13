package com.xjc.tool.yeepay.api;

import com.xjc.tool.yeepay.request.OrderCancelRequest;
import com.xjc.tool.yeepay.request.OrderQueryRequest;
import com.xjc.tool.yeepay.response.OrderCancelResponse;
import com.xjc.tool.yeepay.response.OrderQueryResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface PayApi {

    @GET("https://www.yeepay.com/app-merchant-proxy/node")
    Call<Void> unifiedOrder(@QueryMap(encoded = true) Map<String, String> params);

    @POST("https://cha.yeepay.com/app-merchant-proxy/command")
    Call<OrderQueryResponse> queryOrder(@Body OrderQueryRequest request);

    @POST("https://cha.yeepay.com/app-merchant-proxy/command")
    Call<OrderCancelResponse> cancelOrder(@Body OrderCancelRequest request);
}
