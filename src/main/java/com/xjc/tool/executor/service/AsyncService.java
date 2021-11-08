package com.xjc.tool.executor.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @Version 1.0
 * @ClassName AsyncServiceImpl
 * @Author jiachenXu
 * @Date 2020/4/8
 * @Description
 */
@Slf4j
@Service
@SuppressWarnings("all")
public class AsyncService {

    private static Random random = new Random();

    @Async("asyncServiceExecutor")
    public void task1() throws InterruptedException {
        log.info("第一个");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("完成一，耗时：" + (end - start) + "毫秒");
    }

    @Async("asyncServiceExecutor")
    public void task2() throws InterruptedException {
        log.info("第二个");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("完成二，耗时：" + (end - start) + "毫秒");
    }

    public void task3() throws InterruptedException {
        log.info("第三个");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("完成三，耗时：" + (end - start) + "毫秒");
    }
}
