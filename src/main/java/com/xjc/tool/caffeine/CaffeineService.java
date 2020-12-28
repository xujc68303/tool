package com.xjc.tool.caffeine;

/**
 * @Version 1.0
 * @ClassName CaffeineService
 * @Author jiachenXu
 * @Date 2020/12/28
 * @Description
 */
public interface CaffeineService {

    void put(String key, Object value);

    Object get(String key);

    void delete(String key);

}
