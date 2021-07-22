package com.xjc.tool.mapstruct;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Version 1.0
 * @ClassName DateConverterMapper
 * @Author jiachenXu
 * @Date 2021/3/27
 * @Description 
 */
public class DateConverterMapper {

    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public String asString(Date date) {
        return date != null ? new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).format(date) : null;
    }

    public Date asDate(String date) {
        try {
            return date != null ? new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS).parse(date) : null;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
