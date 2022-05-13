package com.xjc.tool.yeepay.api;

import com.xjc.tool.yeepay.request.OrderCancelRequest;
import com.xjc.tool.yeepay.request.OrderQueryRequest;
import com.xjc.tool.yeepay.request.PayRequest;
import com.xjc.tool.yeepay.response.OrderCancelResponse;
import com.xjc.tool.yeepay.response.OrderQueryResponse;

import java.util.Map;

public interface IYeePay {

    /**
     * 生成支付链接
     */
    String getPayURL(PayRequest request);
    /**
     * 查询订单
     *
     * @param request
     * @return
     */
    OrderQueryResponse queryOrder(OrderQueryRequest request);
    /**
     * 撤销订单
     *
     * @param request
     * @return
     */
    OrderCancelResponse cancelOrder(OrderCancelRequest request);
    /**
     * 验证通知签名
     *
     * @param request
     */
    void validateCallback(Map<String, String> request);
}
