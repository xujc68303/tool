package com.xjc.tool.net;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

/**
 * AddressUtils.java
 *
 * @author Xujc
 * @date 2021/10/20
 */
public class AddressUtils {

    public static String getAddress(String ip) {
        String url = "http://ip.ws.126.net/ipquery?ip=" + ip;
        String str = HttpUtil.get(url);
        if (!StrUtil.hasBlank(str)) {
            String substring = str.substring(str.indexOf("{"), str.indexOf("}") + 1);
            JSONObject jsonObject = JSONObject.parseObject(substring);
            String province = jsonObject.getString("province");
            String city = jsonObject.getString("city");
            return province + " " + city;
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        String address = AddressUtils.getAddress("183.129.146.132");
        System.out.println(address);
    }

}
