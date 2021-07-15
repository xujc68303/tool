package com.xjc.tool.executor;

import net.jodah.expiringmap.internal.NamedThreadFactory;

import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Version 1.0
 * @ClassName ThreadChangeDemo
 * @Author jiachenXu
 * @Date 2021/1/18
 * @Description
 */
public class ThreadChangeDemo {

    public static void main(String[] args) {
        dynamicModifyExecutor();
    }

    private static ThreadPoolExecutor buildThradPoolExecutor() {
        return new ThreadPoolExecutor(2, 5, 60, TimeUnit.SECONDS, new ResizableCapacityLinkedBlockIngQueue<>(10), new NamedThreadFactory("xjc test"));
    }

    private static void dynamicModifyExecutor() {
        ThreadPoolExecutor executor = buildThradPoolExecutor();
        for (int i = 0; i < 15; i++) {
            executor.execute(() -> {
                threadPoolStatus(executor, "create task");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.getStackTrace();
                }
            });
        }
        threadPoolStatus(executor, "动态改变之前");
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.setCorePoolSize(10);
        executor.setMaximumPoolSize(10);
        ResizableCapacityLinkedBlockIngQueue queue = (ResizableCapacityLinkedBlockIngQueue) executor.getQueue();
        queue.setCapacity(100);
        threadPoolStatus(executor, "改变之后");
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static void threadPoolStatus(ThreadPoolExecutor executor, String name) {
        ResizableCapacityLinkedBlockIngQueue blockIngQueue = (ResizableCapacityLinkedBlockIngQueue) executor.getQueue();
        System.out.println(Thread.currentThread().getName() + "-" + name + "-:"
                + "核心线程数：" + executor.getCorePoolSize() + ";"
                + "活动线程数：" + executor.getActiveCount() + ";"
                + "最大线程数" + executor.getMaximumPoolSize() + ";"
                + "任务完成数" + executor.getCompletedTaskCount() + ";"
                + "执行队列大小" + blockIngQueue.size() + ";"
                + "队列剩余空间" + blockIngQueue.remainingCapacity()
        );

    }


}
