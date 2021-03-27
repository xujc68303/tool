package com.xjc.tool.collection;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;


/**
 * @Author jiachenxu
 * @Date 2021/3/27
 * @Descripetion 集合工具类
 */
public class CollectionUtil {

    /**
     * 获取交集
     *
     * @param source 集合1
     * @param target 集合2
     * @return
     */
    public static Collection<?> getIntersection(Set<?> source, Set<?> target) {
        return Sets.intersection(source, target);
    }

    /**
     * 获取差集
     *
     * @param source 集合1
     * @param target 集合2
     * @return
     */
    public static Collection<?> getDifference(Set<?> source, Set<?> target) {
        return Sets.difference(source, target);
    }

    /**
     * 获取并集
     *
     * @param source 集合1
     * @param target 集合2
     * @return
     */
    public static Collection<?> getUnion(Set<?> source, Set<?> target) {
        return Sets.union(source, target);
    }

    /**
     * 获取过滤包含关键词后的集合
     *
     * @param collection 集合
     * @param pattern    过滤词
     * @return 集合
     */
    public static Collection<String> filter(Collection<String> collection, String pattern) {
        return Collections2.filter(collection, Predicates.containsPattern(pattern));
    }

    /**
     * 获取过滤包含多个关键词后的集合
     *
     * @param collection 集合
     * @param pattern1   过滤词
     * @param pattern2   过滤词
     * @return 集合
     */
    public static Collection filterMultiple(Collection<String> collection, String pattern1, String pattern2) {
        return Collections2.filter(collection, Predicates.or(Predicates.containsPattern(pattern1), Predicates.containsPattern(pattern2)));
    }

    /**
     * 获取过滤包含多个关键词后的集合
     *
     * @param collection 集合
     * @param pattern    包含词
     * @param notPattern 剔除词
     * @return 集合
     */
    public static Collection filterNotMultiple(Collection<String> collection, String pattern, String notPattern) {
        return Collections2.filter(collection, Predicates.or(Predicates.containsPattern(pattern), Predicates.not(Predicates.containsPattern(notPattern))));
    }

    /**
     * 获取大于等于pattern集合
     *
     * @param collection 集合
     * @param pattern    过滤参数
     * @return
     */
    public static Collection filterGreater(Collection<Integer> collection, Integer pattern) {
        return Collections2.filter(collection, new Predicate<Integer>() {
            @Override
            public boolean apply(@Nullable Integer integer) {
                return integer >= pattern;
            }
        });
    }

    /**
     * 获取小于等于pattern的集合
     *
     * @param collection 集合
     * @param pattern    过滤参数
     * @return
     */
    public static Collection<Integer> filterLess(Collection<Integer> collection, Integer pattern) {
        return Collections2.filter(collection, new Predicate<Integer>() {
            @Override
            public boolean apply(@Nullable Integer integer) {
                return integer <= pattern;
            }
        });
    }

    /**
     * 对集合每个元素进行字符串修饰
     *
     * @param collection 集合
     * @param str        修饰词
     * @return
     */
    public static Collection<?> transform(Collection<?> collection, String str) {
        return Collections2.transform(collection, new Function<Object, Object>() {
            @Nullable
            @Override
            public Object apply(@Nullable Object input) {
                return input + str;
            }
        });
    }

    /**
     * 对集合每个元素进行计算
     *
     * @param collection 集合
     * @param i          值
     * @param operator   计算模型
     * @return 返回的是一个视图，不能对结果集合操作
     */
    public static Collection<?> transform(Collection<Integer> collection, Integer i, Operator operator) {
        return Collections2.transform(collection, functionHandle(operator, i));
    }

    private static Function functionHandle(Operator operator, Integer i) {
        switch (operator) {
            case PLUS:
                return new Function<Integer, Integer>() {
                    @Nullable
                    @Override
                    public Integer apply(@Nullable Integer input) {
                        return input + i;
                    }
                };
            case SUBTRACT:
                return new Function<Integer, Integer>() {
                    @Nullable
                    @Override
                    public Integer apply(@Nullable Integer input) {
                        if (input < i) {
                            return 0;
                        }
                        return input - i;
                    }
                };
            default:
                return null;
        }
    }

    enum Operator {
        PLUS, SUBTRACT
    }


}
