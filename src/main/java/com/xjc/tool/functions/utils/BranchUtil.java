package com.xjc.tool.functions.utils;

import com.xjc.tool.functions.imterface.BranchHandle;

/**
 * @Author jiachenxu
 * @Date 2021/12/6
 * @Descripetion
 */
public class BranchUtil {

    public static BranchHandle isTrueOrFalse(boolean b) {
        return (t, y) -> {
            if (b) {
                t.run();
            } else {
                y.run();
            }
        };
    }

    public static void main(String[] args) {
        BranchUtil.isTrueOrFalse(true)
                .trueOrFalseHandle(() -> System.out.println(true), () -> System.out.println(false));
    }
}
