package com.xjc.tool.caffeine;

import com.github.benmanes.caffeine.cache.*;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import javax.annotation.PostConstruct;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @Version 1.0
 * @ClassName CaffeineServiceImpl
 * @Author jiachenXu
 * @Date 2020/12/28
 * @Description
 */
@Slf4j
public class CaffeineServiceImpl implements CaffeineService{

    private long maximumSize = 256L;

    private int initialCapacity = 10;

    private long durationWrite = 10;

    private long asyncwrite = 10;

    private Cache<String, Object> cache;

    @PostConstruct
    private void init(){
        cache = Caffeine.newBuilder()
                .maximumSize(maximumSize)
                .initialCapacity(initialCapacity)
                .expireAfterWrite(durationWrite, TimeUnit.SECONDS)
                .refreshAfterWrite(asyncwrite, TimeUnit.SECONDS)
                .recordStats()
                .writer(new CacheWriter<String, Object>( ) {

                    @Override
                    public void write(@NonNull String key, @NonNull Object value) {
                        log.warn("key={}, CacheWriter write", key);
                    }

                    @Override
                    public void delete(@NonNull String key, @Nullable Object value, @NonNull RemovalCause removalCause) {
                        log.warn("key={}, cause={}, CacheWriter delete", key, removalCause);
                    }
                })
                .build(new CacheLoader<Object, Object>() {

                    @Nullable
                    @Override
                    public Object load(@NonNull Object o) throws Exception {
                        // 同步加载
                        return null;
                    }

                    @Override
                    public @NonNull CompletableFuture<Object> asyncLoad(@NonNull Object key, @NonNull Executor executor) {
                        // 异步加载
                        return null;
                    }
                });
    }

    @Override
    public void put(String key, Object value) {
        cache.put(key, value);
    }

    @Override
    public Object get(String key) {
        return cache.get(key,  k-> setValue(key).apply(key));
    }

    @Override
    public void delete(String key) {
        cache.invalidate(key);
    }

    /**
     * 如果一个key不存在，那么生成value
     * @param key
     * @return
     */
    private Function<String, Object> setValue(String key){
        return t -> key + "value";
    }
}
