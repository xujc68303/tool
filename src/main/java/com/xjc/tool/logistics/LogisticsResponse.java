package com.xjc.tool.logistics;

import lombok.Data;

/**
 * @Author jiachenxu
 * @Date 2021/12/2
 * @Descripetion
 */
@Data
public class LogisticsResponse {

    private String status;
    private String msg;
    private LogisticsRsult result;
}
