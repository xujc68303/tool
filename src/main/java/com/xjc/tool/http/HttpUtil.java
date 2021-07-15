package com.xjc.tool.http;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * @Version 1.0
 * @ClassName FilesServiceImpl
 * @Author jiachenXu
 * @Date 2021/7/8
 * @Description
 */
public class HttpUtil {

    private static final String contentType = "application/json";

    public static HttpResponse httpGet(String url, Map<String, Object> paramMap) {
        return httpGet(url, null, paramMap);
    }

    public static HttpResponse httpGet(String url, Map<String, String> headerMap, int timeout) {
        return httpGet(url, headerMap, null, timeout);
    }

    public static HttpResponse httpGet(String url, Map<String, String> headerMap, Map<String, Object> paramMap) {
        return httpGet(url, headerMap, paramMap, 30000);
    }

    public static HttpResponse httpGet(String url, Map<String, String> headerMap, Map<String, Object> paramMap, int timeout) {
        return HttpRequest.get(url)
                .headerMap(headerMap, true)
                .form(paramMap)
                .timeout(timeout)
                .setSSLSocketFactory(SSLSocketClientUtil.socketFactory(SSLSocketClientUtil.x509TrustManager()))
                .setHostnameVerifier(SSLSocketClientUtil.hostnameVerifier())
                .execute();
    }

    public static HttpResponse httpPost(String url, Map<String, Object> paramMap) {
        return httpPost(url, null, paramMap, 30000);
    }

    public static HttpResponse httpPost(String url, Map<String, Object> paramMap, int timeout) {
        return httpPost(url, null, paramMap, timeout);
    }

    public static HttpResponse httpPost(String url, Map<String, String> headerMap, Map<String, Object> paramMap, int timeout) {
        return HttpRequest.post(url)
                .headerMap(headerMap, true)
                .body(JSONObject.toJSONString(paramMap), contentType)
                .timeout(timeout)
                .setSSLSocketFactory(SSLSocketClientUtil.socketFactory(SSLSocketClientUtil.x509TrustManager()))
                .setHostnameVerifier(SSLSocketClientUtil.hostnameVerifier())
                .execute();
    }

}
