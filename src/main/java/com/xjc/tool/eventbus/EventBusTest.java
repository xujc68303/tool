package com.xjc.tool.eventbus;

import com.xjc.tool.eventbus.event.EventBusModel;
import com.xjc.tool.eventbus.listener.EventListener;
import com.xjc.tool.eventbus.send.EventBusUtil;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Version 1.0
 * @ClassName EventBusTest
 * @Author jiachenXu
 * @Date 2021/7/9
 * @Description
 */
@Slf4j
public class EventBusTest {

    public static void main(String[] args) {

        EventBusUtil eventBusUtil = new EventBusUtil();
        EventListener eventListener = new EventListener();
        eventBusUtil.getAsyncEventBus();
        eventBusUtil.register(eventListener);
        EventBusModel event = new EventBusModel();
        event.setId("1")
                .setMessage("消息总线异步发送")
                .setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        eventBusUtil.asyncSend(event);
        log.info("主线程发送完毕");
    }

}
