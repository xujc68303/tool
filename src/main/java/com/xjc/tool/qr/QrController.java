package com.xjc.tool.qr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author jiachenxu
 * @Date 2022/1/1
 * @Descripetion
 */
@RestController
@RequestMapping("/qr")
public class QrController {

    @Autowired
    private QrUtil qrUtil;

    @RequestMapping("/quTest")
    public void quTest(HttpServletResponse response) throws Exception {
        String url = "http://www.baidu.com";
        qrUtil.generate(url, response);
    }

    public static void main(String[] args) {
        Calendar instance1 = Calendar.getInstance();
        instance1.setTimeInMillis(System.currentTimeMillis());
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.MINUTE, -3);
        String datePoor = getDatePoor(instance1.getTimeInMillis(), instance.getTimeInMillis());
        System.out.println(datePoor);
    }

    public static String getDatePoor(long endDate, long nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long ns = 1000;
        long diff = endDate - nowDate;
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟" + sec + "秒";
    }
}
