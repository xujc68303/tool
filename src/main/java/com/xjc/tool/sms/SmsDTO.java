package com.xjc.tool.sms;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author jiachenxu
 * @Date 2021/12/2
 * @Descripetion
 */
@Data
@AllArgsConstructor
public class SmsDTO {

    private String phone;
    private String requestId;
    private String code;

}
