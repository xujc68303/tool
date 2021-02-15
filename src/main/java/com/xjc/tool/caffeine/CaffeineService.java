package com.xjc.tool.caffeine;

import com.github.benmanes.caffeine.cache.stats.CacheStats;

import java.util.List;
import java.util.Map;

/**
 * @Version 1.0
 * @ClassName CaffeineService
 * @Author jiachenXu
 * @Date 2020/12/28
 * @Description 内存缓存
 */
public interface CaffeineService {

    /**
     * 缓存数据，旧值被新值替换
     *
     * @param key   key
     * @param value value
     */
    void put(String key, Object value);

    /**
     * 批量缓存数据，旧值被新值替换
     *
     * @param map map
     */
    void putAll(Map<String, Object> map);

    /**
     * 根据key获取，key为null返回null
     *
     * @param key key
     * @return value
     */
    Object getPresent(String key);

    /**
     * 根据key获取批量，key为null返回null
     *
     * @param keys keys
     * @return map
     */
    Map<String, Object> getAllPresent(List<String> keys);

    /**
     * 根据key删除
     *
     * @param key key
     */
    void invalidate(String key);

    /**
     * 根据key批量删除
     *
     * @param keys keys
     */
    void invalidateAll(List<String> keys);

    /**
     * 清空缓存
     */
    void clear();

    /**
     * 获取缓存状态
     *
     * @return 缓存状态
     */
    CacheStats getStats();

    /**
     * 获取缓存长度
     *
     * @return
     */
    long size();

}
