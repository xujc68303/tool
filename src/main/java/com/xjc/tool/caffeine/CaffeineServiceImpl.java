package com.xjc.tool.caffeine;

import com.github.benmanes.caffeine.cache.*;
import com.github.benmanes.caffeine.cache.stats.CacheStats;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Version 1.0
 * @ClassName CaffeineServiceImpl
 * @Author jiachenXu
 * @Date 2020/12/28
 * @Description
 */
@Slf4j
@Service
@SuppressWarnings("all")
public class CaffeineServiceImpl implements CaffeineService{

    private long maximumSize = 256L;

    private int initialCapacity = 10;

    private long durationWrite = 10;

    private long asyncWrite = 10;

    private Cache<String, Object> cache;

    @PostConstruct
    private void init(){
        cache = Caffeine.newBuilder()
                .recordStats()
                .maximumSize(maximumSize)
                .initialCapacity(initialCapacity)
//                .expireAfterWrite(durationWrite, TimeUnit.SECONDS)
                .refreshAfterWrite(asyncWrite, TimeUnit.SECONDS)
                .removalListener(new RemovalListener<String, Object>( ) {

                    /**
                     * Notifies the listener that a removal occurred at some point in the past.
                     * <p>
                     * This does not always signify that the key is now absent from the cache, as it may have already
                     * been re-added.
                     *
                     * @param key   the key represented by this entry, or {@code null} if collected
                     * @param value the value represented by this entry, or {@code null} if collected
                     * @param cause the reason for which the entry was removed
                     */
                    @Override
                    public void onRemoval(@Nullable String key, @Nullable Object value, @NonNull RemovalCause cause) {
                        log.warn("key:{}被移除， cause={}", key, cause);
                    }
                })
                .writer(new CacheWriter<String, Object>( ) {

                    @Override
                    public void write(@NonNull String key, @NonNull Object value) {
                        log.warn("key={}, CacheWriter write", key);
                    }

                    @Override
                    public void delete(@NonNull String key, @Nullable Object value, @NonNull RemovalCause removalCause) {
                        log.warn("key={}, cause={}, CacheWriter delete", key, removalCause);
                    }
                }).build();
    }

    @Override
    public void put(String key, Object value) {
        cache.put(key, value);
    }

    @Override
    public void putAll(Map<String, Object> map) {
        cache.putAll(map);
    }

    @Override
    public Object getPresent(String key) {
        return cache.getIfPresent(key);
    }

    @Override
    public Map<String, Object> getAllPresent(List<String> keys) {
        return cache.getAllPresent(keys);
    }

    @Override
    public void invalidate(String key) {
        cache.invalidate(key);
    }

    @Override
    public void invalidateAll(List<String> keys) {
        cache.invalidateAll(keys);
    }

    @Override
    public void clear() {
        cache.invalidateAll();
    }

    @Override
    public CacheStats getStats() {
        return cache.stats();
    }

    @Override
    public long size() {
        cache.cleanUp();
        return cache.estimatedSize();
    }
}
