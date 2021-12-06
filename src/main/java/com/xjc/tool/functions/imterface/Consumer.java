package com.xjc.tool.functions.imterface;

import java.util.Objects;

/**
 * @Author jiachenxu
 * @Date 2021/12/6
 * @Descripetion
 */
@FunctionalInterface
public interface Consumer<T> {


    void accept(T t);

    default Consumer<T> andThen(Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> {
            accept(t);
            after.accept(t);
        };
    }
}
