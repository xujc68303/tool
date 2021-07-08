package com.xjc.tool.eventbus.event;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Version 1.0
 * @ClassName EventBusModel
 * @Author jiachenXu
 * @Date 2021/7/9
 * @Description 事件类
 */
@Data
@Accessors(chain = true)
public class EventBusModel implements Serializable {
    private static final long serialVersionUID = -2716698530222539977L;

    private String id;

    private String message;

    private String time;
}
