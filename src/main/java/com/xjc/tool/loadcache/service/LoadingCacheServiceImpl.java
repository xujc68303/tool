package com.xjc.tool.loadcache.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.xjc.tool.loadcache.api.LoadingCacheService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Version 1.0
 * @ClassName LoadingCacheServiceImpl
 * @Author jiachenXu
 * @Date 2020/9/1
 * @Description
 */
@Slf4j
@Service
@SuppressWarnings("all")
public class LoadingCacheServiceImpl extends Thread implements LoadingCacheService {

    /**
     * 缓存容量最大值，超过最大值LRU进行清除
     */
    private volatile static long MAX_SIZE = 10000;

    /**
     * 初始化缓存容量
     */
    private volatile static int INITI_ALCAPA_CITY = 1000;

    /**
     * 缓存过期时间
     */
    private volatile static long DURA_TION = 60 * 60;

    /**
     * 缓存刷新时间
     */
    private volatile static long REFRESH = 60;

    /**
     * 时间单位秒
     */
    private static TimeUnit UNIT = TimeUnit.SECONDS;

    private static final ScheduledExecutorService scheduledexecutorservice = Executors.newSingleThreadScheduledExecutor();

    private static ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

    private static ThreadLocal<Object> threadLocal;

    private static volatile LoadingCache<String, Object> loadingCache;


    static {
        synchronized (LoadingCache.class) {
            loadingCache = CacheBuilder
                    .newBuilder( )
                    .recordStats( )
                    .maximumSize(MAX_SIZE)
                    .initialCapacity(INITI_ALCAPA_CITY)
                    .refreshAfterWrite(REFRESH, UNIT)
                    .removalListener((monitor) -> {
                        log.info("key:{}被移除", monitor.getKey( ));
                    })
                    .build(new CacheLoader<String, Object>( ) {

                        @Override
                        public Object load(@NotNull String key) throws Exception {
                            return callable.call();
                        }

                        @Override
                        public ListenableFuture<Object> reload(String key, Object oldValue) throws Exception {
                            log.warn("......后台线程池异步刷新:" + key);
                            threadLocal.set(key);
                            return service.submit(callable);
                        }
                    });

            asyncRefresh( );
        }
    }

    // 模拟一个需要耗时2s的数据库查询任务
    private static Callable<Object> callable = new Callable<Object>() {
        @Override
        public Object call() throws Exception {
            String k = threadLocal.get().toString();
            System.out.println("begin to mock query db..."+ k);
            Thread.sleep(2000);
            System.out.println("success to mock query db..."+ k);
            threadLocal.remove();
            return UUID.randomUUID();
        }
    };


    @Override
    public void run() {
        this.asMap( ).keySet( ).forEach(k -> loadingCache.refresh(k));
    }

    /**
     * 定时刷新
     */
    private static void asyncRefresh() {
        try {
            scheduledexecutorservice.scheduleAtFixedRate(new LoadingCacheServiceImpl( ), 0, REFRESH, UNIT);
        } catch (RuntimeException e) {
            scheduledexecutorservice.shutdown( );
        }
    }

    @Override
    public void modifyConfig(long maxSize, long duration, TimeUnit timeUnit, int initialCapacity, long refresh) {
        if (maxSize == 0 || duration == 0 || timeUnit == null || initialCapacity == 0 || refresh == 0) {
            return;
        }
        MAX_SIZE = maxSize;
        DURA_TION = duration;
        UNIT = timeUnit;
        INITI_ALCAPA_CITY = initialCapacity;
        REFRESH = refresh;
    }

    @Override
    public boolean put(String key, Object value) {
        Map<String, Object> result = Maps.newLinkedHashMap( );
        result.put(key, value);
        return this.putAll(result);
    }

    @Override
    public boolean putAll(Map<String, Object> map) {
        loadingCache.putAll(map);
        return true;
    }

    @Override
    public Object get(String key) {
        List<String> keys = Arrays.asList(key);
        return this.getAll(keys);
    }

    @Override
    public Map<String, Object> getAll(List<String> keys) {
        return loadingCache.getAllPresent(keys);
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> result = Maps.newLinkedHashMap( );
        loadingCache.asMap( ).forEach(result::put);
        return result;
    }

    @Override
    public boolean delete(String key) {
        List<String> keys = Arrays.asList(key);
        return this.deleteAll(keys);
    }

    @Override
    public boolean deleteAll(List<String> keys) {
        loadingCache.invalidateAll(keys);
        return true;
    }

    @Override
    public long size() {
        return loadingCache.size( );
    }

    @Override
    public boolean clear() {
        loadingCache.invalidateAll( );
        return true;
    }
}
