package com.xjc.tool.logistics;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @Author jiachenxu
 * @Date 2021/12/2
 * @Descripetion
 */
public class LogisticsService {

    private static final String host = "https://wuliu.market.alicloudapi.com";
    private static final String path = "/kdi";
    private static final String appcode = "1a6d78a98b334615a8afe68d66dc3f82";


    public LogisticsResponse catNo(String no, String type) {
        try {
            String urlSend;
            if (StringUtils.isNotBlank(type)) {
                urlSend = host + path + "?no=" + no + "&type=" + type;
            } else {
                urlSend = host + path + "?no=" + no;
            }

            URL url = new URL(urlSend);
            HttpURLConnection httpURLCon = (HttpURLConnection) url.openConnection();
            httpURLCon.setRequestMethod("GET");
            httpURLCon.setRequestProperty("Authorization", "APPCODE " + appcode);
            int httpCode = httpURLCon.getResponseCode();
            if (httpCode == 200) {
                String json = read(httpURLCon.getInputStream());
                return JSONObject.parseObject(json, LogisticsResponse.class);
            } else {
                Map<String, List<String>> map = httpURLCon.getHeaderFields();
                String error = map.get("X-Ca-Error-Message").get(0);
                if (httpCode == 400 && error.equals("Invalid AppCode `not exists`")) {
                    System.out.println("AppCode错误 ");
                } else if (httpCode == 400 && error.equals("Invalid Url")) {
                    System.out.println("请求的 Method、Path 或者环境错误");
                } else if (httpCode == 400 && error.equals("Invalid Param Location")) {
                    System.out.println("参数错误");
                } else if (httpCode == 403 && error.equals("Unauthorized")) {
                    System.out.println("服务未被授权（或URL和Path不正确）");
                } else if (httpCode == 403 && error.equals("Quota Exhausted")) {
                    System.out.println("套餐包次数用完 ");
                } else {
                    System.out.println("参数名错误 或 其他错误");
                    System.out.println(error);
                }
            }
        } catch (Exception e) {
             e.printStackTrace();
        }
        return null;
    }


    private static String read(InputStream is) throws IOException {
        StringBuffer sb = new StringBuffer();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                line = new String(line.getBytes(), StandardCharsets.UTF_8);
                sb.append(line);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String no = "";
        LogisticsService service = new LogisticsService();
        LogisticsResponse logisticsResponse = service.catNo(no, null);
        System.out.println(logisticsResponse);
    }

}
