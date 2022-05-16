package com.xjc.tool.yeepay.client;

import com.xjc.tool.yeepay.config.YeePayConfig;
import com.xjc.tool.yeepay.engine.YeePayEngine;
import com.xjc.tool.yeepay.request.OrderCancelRequest;
import com.xjc.tool.yeepay.request.OrderQueryRequest;
import com.xjc.tool.yeepay.request.PayRequest;
import com.xjc.tool.yeepay.response.CancelOrderResponse;
import com.xjc.tool.yeepay.response.NotifyOrderResponse;
import com.xjc.tool.yeepay.response.QueryOrderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * YeePayClient.java
 *
 * @author Xujc
 * @date 2022/5/16
 */
public class YeePayClient {

    private static final Logger logger = LoggerFactory.getLogger(YeePayClient.class);

    private static YeePayEngine yeePayEngine;

    public YeePayClient(YeePayConfig yeePayConfig) {
        yeePayEngine = new YeePayEngine(yeePayConfig);
    }

    public String unifiedOrder(String body, String fee, String payType) {
        PayRequest request = new PayRequest();
        request.setP1_MerId(yeePayEngine.getMerId());
        request.setP5_Pid(body);
        request.setP3_Amt(fee);
        request.setPd_FrpId(payType);
        return yeePayEngine.getPayURL(request);
    }

    public QueryOrderResponse queryOrder(String mOrderNo) {
        OrderQueryRequest request = new OrderQueryRequest();
        request.setP2_Order(mOrderNo);
        return yeePayEngine.queryOrder(request);
    }

    public NotifyOrderResponse notifyOrder(Map<String, String> request) {
        NotifyOrderResponse response = new NotifyOrderResponse();
        try {
            yeePayEngine.validateCallback(request);
            response.setCode(1);
            response.setMsg("成功");
        } catch (Exception e) {
            logger.error("回调验签失败");
            response.setCode(500);
            response.setMsg("回调验签失败");
        }
        return response;
    }

    public CancelOrderResponse cancelOrder(String txOrderId) {
        OrderCancelRequest orderCancelRequest = new OrderCancelRequest();
        orderCancelRequest.setP1_MerId(yeePayEngine.getMerId());
        orderCancelRequest.setPb_TrxId(txOrderId);
        return yeePayEngine.cancelOrder(orderCancelRequest);
    }

}
