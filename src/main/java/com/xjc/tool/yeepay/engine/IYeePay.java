package com.xjc.tool.yeepay.engine;

import com.xjc.tool.yeepay.request.OrderCancelRequest;
import com.xjc.tool.yeepay.request.OrderQueryRequest;
import com.xjc.tool.yeepay.request.PayRequest;
import com.xjc.tool.yeepay.response.CancelOrderResponse;
import com.xjc.tool.yeepay.response.QueryOrderResponse;

import java.util.Map;

/**
 * IYeePay.java
 *
 * @author Xujc
 * @date 2022/5/16
 */
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
    QueryOrderResponse queryOrder(OrderQueryRequest request);
    /**
     * 撤销订单
     *
     * @param request
     * @return
     */
    CancelOrderResponse cancelOrder(OrderCancelRequest request);
    /**
     * 验证通知签名
     *
     * @param request
     */
    void validateCallback(Map<String, String> request);
}
