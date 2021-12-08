package com.xjc.tool.functions.imterface;

/**
 * @Author jiachenxu
 * @Date 2021/12/6
 * @Descripetion
 */
@FunctionalInterface
public interface PresentOrElseHandler<T extends Object> {

    void presentOrElseHandle(Consumer<? super T> action, Runnable emptyAction);

}
