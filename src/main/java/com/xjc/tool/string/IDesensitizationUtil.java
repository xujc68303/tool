package com.xjc.tool.string;

/**
 * @Version 1.0
 * @ClassName IDesensitizationUtil
 * @Author jiachenXu
 * @Date 2021/2/2
 * @Description 脱敏工具类
 */
public class IDesensitizationUtil {

    private static String REPLACE_CHAR = "*";

    private static String FORMAT = "(?<=\\w{%d})\\w(?=\\w{%d})";

    public String around(String str, int left, int right) {
        if (str == null || (str.length() < left + right + 1)) return str;
        return str.replaceAll(String.format(FORMAT, left, right), REPLACE_CHAR);
    }

}
