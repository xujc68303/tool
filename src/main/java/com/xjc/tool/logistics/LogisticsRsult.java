package com.xjc.tool.logistics;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author jiachenxu
 * @Date 2021/12/2
 * @Descripetion
 */
@Data
public class LogisticsRsult {

    private String number;
    private String type;
    private List<LogisticsDetails> list;
    private String deliverystatus;
    private String issign;
    private String expName;
    private String expSite;
    private String expPhone;
    private String logo;
    private String courier;
    private String courierPhone;
    private Date updateTime;
    private String takeTime;
}
