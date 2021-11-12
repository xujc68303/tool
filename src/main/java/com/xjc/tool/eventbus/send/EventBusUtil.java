package com.xjc.tool.eventbus.send;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.xjc.tool.eventbus.event.EventBusModel;
import com.xjc.tool.eventbus.listener.EventListener;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Version 1.0
 * @ClassName EventBusUtil
 * @Author jiachenXu
 * @Date 2021/7/9
 * @Description 事件总线
 */
public class EventBusUtil {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(4);

    private volatile EventBus eventBus;

    private volatile AsyncEventBus asyncEventBus;

    public EventBus getEventBus() {
        if (Objects.isNull(eventBus)) {
            synchronized (EventBusUtil.class) {
                if (Objects.isNull(eventBus)) {
                    eventBus = new EventBus();
                }
            }
        }
        return eventBus;
    }

    public AsyncEventBus getAsyncEventBus() {
        if (Objects.isNull(asyncEventBus)) {
            synchronized (EventBusUtil.class) {
                if (Objects.isNull(asyncEventBus)) {
                    asyncEventBus = new AsyncEventBus(executorService);
                }
            }
        }
        return asyncEventBus;
    }

    public void register(EventListener eventListener) {
        getEventBus().register(eventListener);
        getAsyncEventBus().register(eventListener);
    }

    public void unRegister(EventListener eventListener) {
        getEventBus().unregister(eventListener);
        getAsyncEventBus().unregister(eventListener);
    }

    public void syncSend(EventBusModel eventBusModel) {
        getEventBus().post(eventBusModel);
    }

    public void asyncSend(EventBusModel eventBusModel) {
        getAsyncEventBus().post(eventBusModel);
    }


}
