package com.xjc.tool.eventbus.listener;

import com.alibaba.fastjson.JSONObject;
import com.google.common.eventbus.Subscribe;
import com.xjc.tool.eventbus.event.EventBusModel;
import lombok.extern.slf4j.Slf4j;

/**
 * @Version 1.0
 * @ClassName EventListener
 * @Author jiachenXu
 * @Date 2021/7/9
 * @Description 事件监听
 */
@Slf4j
public class EventListener {

    @Subscribe
    public void listener(EventBusModel eventBusModel){
        log.info("当前线程:{}, 监听事件：{}", Thread.currentThread().getId(), JSONObject.toJSONString(eventBusModel));
    }
}
