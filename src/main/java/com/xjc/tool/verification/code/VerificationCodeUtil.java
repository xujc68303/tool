package com.xjc.tool.verification.code;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.captcha.generator.RandomGenerator;

import java.awt.*;

/**
 * @Description 验证码生成
 * @Date 2021/7/5 16:45
 * @Created by Xujc
 */
public class VerificationCodeUtil {

    private static LineCaptcha lineCaptcha;

    public VerificationCodeUtil() {
        lineCaptcha = CaptchaUtil.createLineCaptcha(230, 130, 4, 200);
        lineCaptcha.setGenerator(new RandomGenerator(4));
        lineCaptcha.setBackground(Color.WHITE);
    }

    public void create(String path) {
        lineCaptcha.write(path);
    }

    public String getCode() {
        return lineCaptcha.getCode();
    }

    public boolean isVerify(String code) {
        return lineCaptcha.verify(code);
    }

    public static void main(String[] args) {
        VerificationCodeUtil verificationCodeUtil = new VerificationCodeUtil();
        verificationCodeUtil.create("d:/line.png");
        System.out.println(verificationCodeUtil.getCode());
    }

}
