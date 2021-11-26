package com.xjc.tool.date;

import java.io.Serializable;

/**
 * TimeModel.java
 *
 * @author Xujc
 * @date 2021/11/26
 */
public class TimeModel implements Serializable {

    private Long day;

    private Long hour;

    private Long minute;

    private Long second;

    public Long getDay() {
        return day;
    }

    public void setDay(Long day) {
        this.day = day;
    }

    public Long getHour() {
        return hour;
    }

    public void setHour(Long hour) {
        this.hour = hour;
    }

    public Long getMinute() {
        return minute;
    }

    public void setMinute(Long minute) {
        this.minute = minute;
    }

    public Long getSecond() {
        return second;
    }

    public void setSecond(Long second) {
        this.second = second;
    }
}
