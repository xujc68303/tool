package com.xjc.tool.logistics;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.xjc.tool.http.HttpUtil;
import com.xjc.tool.string.StringUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * @Author jiachenxu
 * @Date 2021/12/2
 * @Descripetion
 */
public class LogisticsService {

    String appcode = "1a6d78a98b334615a8afe68d66dc3f82";

    public LogisticsResponse catNo(String no, String type, String phone) {
        try {
            if (no.startsWith("SF") && StringUtil.isBlank(phone)) {
                return null;
            } else {
                no = no + ":" + phone.substring(phone.length() - 4);
            }
            String urlSend = "https://wuliu.market.alicloudapi.com/kdi?no=" + no + "&type=" + type;
            if (StringUtils.isBlank(type)) {
                urlSend = "https://wuliu.market.alicloudapi.com/kdi?no=" + no;
            }
            Map<String, String> header = Maps.newHashMap();
            header.put("Authorization", "APPCODE " + appcode);
            HttpResponse httpResponse = HttpUtil.httpGet(urlSend, header, 3000);
            int httpCode = httpResponse.getStatus();
            if (httpCode == 200) {
                String json = httpResponse.body();
                return JSONObject.parseObject(json, LogisticsResponse.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String exCompany(String no){
        String urlSend = "https://wuliu.market.alicloudapi.com/exCompany?no=" + no;
        Map<String, String> header = Maps.newHashMap();
        header.put("Authorization", "APPCODE " + appcode);
        HttpResponse httpResponse = HttpUtil.httpGet(urlSend, header, 3000);
        if(200 == httpResponse.getStatus()){
            return httpResponse.body();
        }
        return null;
    }

    public static void main(String[] args) {
        String no = "432240570187671";
        LogisticsService service = new LogisticsService();
        LogisticsResponse logisticsResponse = service.catNo(no, null, "17600792030");
        System.out.println(logisticsResponse);
        String s = service.exCompany(no);
        System.out.println(s);
    }

}
