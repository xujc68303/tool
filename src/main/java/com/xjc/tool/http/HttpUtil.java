package com.xjc.tool.http;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

import java.util.Map;

/**
 * @Version 1.0
 * @ClassName FilesServiceImpl
 * @Author jiachenXu
 * @Date 2021/7/8
 * @Description
 */
public class HttpUtil {

    public HttpResponse httpGet(String url, Map<String, Object> paramMap) {
        return httpGet(url, null, paramMap);
    }

    public HttpResponse httpGet(String url, Map<String, String> headerMap, Map<String, Object> paramMap) {
        return httpGet(url, headerMap, paramMap, 3000);
    }

    public HttpResponse httpGet(String url, Map<String, String> headerMap, Map<String, Object> paramMap, int timeout) {
        return HttpRequest.get(url).headerMap(headerMap, true).form(paramMap).timeout(timeout).execute();
    }

    public HttpResponse httpPost(String url, Map<String, Object> paramMap) {
        return httpPost(url, null, paramMap, 3000);
    }

    public HttpResponse httpPost(String url, Map<String, Object> paramMap, int timeout) {
        return httpPost(url, null, paramMap, timeout);
    }

    public HttpResponse httpPost(String url, Map<String, String> headerMap, Map<String, Object> paramMap, int timeout) {
        return HttpRequest.post(url).headerMap(headerMap, true).form(paramMap).timeout(timeout).execute();
    }

}
