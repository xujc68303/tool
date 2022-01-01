package com.xjc.tool.qr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

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
}
