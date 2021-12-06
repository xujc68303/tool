package com.xjc.tool.functions.utils;

import com.xjc.tool.functions.imterface.ThrowExceptionFunction;

/**
 * @Author jiachenxu
 * @Date 2021/12/6
 * @Descripetion
 */
public class ParamUtil {

    public static ThrowExceptionFunction isTrue(boolean b) {
        return (e) -> {
            if (b) {
                throw new RuntimeException(e);
            }
        };
    }

    public static void main(String[] args) {
        ParamUtil.isTrue(true).throwMessage("参数错误");
    }
}
