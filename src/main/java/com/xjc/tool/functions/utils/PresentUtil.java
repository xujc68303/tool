package com.xjc.tool.functions.utils;

import com.xjc.tool.functions.imterface.PresentOrElseHandler;

/**
 * @Author jiachenxu
 * @Date 2021/12/6
 * @Descripetion
 */
public class PresentUtil {


    public static PresentOrElseHandler<String> isBlankOrNoBlank(String str){
        return (t, y) -> {
            if(null != str){
                y.run();
            } else {
                t.accept(str);
            }
        };
    }

    public static void main(String[] args) {
        PresentUtil.isBlankOrNoBlank("hello").presentOrElseHandle(System.out::println, () -> System.out.println("空参数"));
    }
}
