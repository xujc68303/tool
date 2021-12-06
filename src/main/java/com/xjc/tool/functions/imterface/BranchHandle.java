package com.xjc.tool.functions.imterface;

/**
 * @Author jiachenxu
 * @Date 2021/12/6
 * @Descripetion
 */
@FunctionalInterface
public interface BranchHandle {

    void trueOrFalseHandle(Runnable trueHandle, Runnable falseHandle);
}
