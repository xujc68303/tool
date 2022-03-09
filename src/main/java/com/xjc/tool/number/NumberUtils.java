package com.xjc.tool.number;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;

/**
 * @Author jiachenxu
 * @Date 2022/3/9
 * @Descripetion
 */
public class NumberUtils {

    public static BigDecimal getGratuity(BigDecimal price, String discount) throws ParseException {
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMaximumFractionDigits(1);
        Number parse = numberFormat.parse(discount);
        return price.multiply(new BigDecimal(parse.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP))
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static double convertDecimal(String str) throws ParseException {
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMaximumFractionDigits(1);
        Number parse = numberFormat.parse(str);
        return parse.doubleValue();
    }

    public static String convertPercentage(double i) {
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMaximumFractionDigits(1);
        return numberFormat.format(i);
    }

    public static void main(String[] args) throws ParseException {
        String str = "29%";
        System.out.println(convertDecimal(str));
    }
}
