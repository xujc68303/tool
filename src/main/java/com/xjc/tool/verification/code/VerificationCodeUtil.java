package com.xjc.tool.verification.code;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.http.server.HttpServerResponse;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Description 验证码生成
 * @Date 2021/7/5 16:45
 * @Created by Xujc
 */
public class VerificationCodeUtil {

    private static LineCaptcha lineCaptcha;

    @PostConstruct
    public void init() {
        lineCaptcha = CaptchaUtil.createLineCaptcha(230, 130);
        lineCaptcha.setGenerator(new RandomGenerator(4));
        lineCaptcha.setBackground(Color.BLACK);
    }

    public static void get(HttpServerResponse response) throws IOException {
        try (OutputStream out = response.getOut()) {
            lineCaptcha.write(out);
        }
    }

    public static String getCode() {
        return lineCaptcha.getCode();
    }

    public static boolean isVerify(String code) {
        return lineCaptcha.verify(code);
    }

}
