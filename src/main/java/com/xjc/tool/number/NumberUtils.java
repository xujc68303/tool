package com.xjc.tool.number;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Random;

/**
 * @Author jiachenxu
 * @Date 2022/3/9
 * @Descripetion
 */
public class NumberUtils {

    private static final char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public static BigDecimal getGratuity(BigDecimal price, String discount) throws ParseException {
        NumberFormat numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMaximumFractionDigits(1);
        Number parse = numberFormat.parse(discount);
        return price.multiply(new BigDecimal(parse.toString()).setScale(2, BigDecimal.ROUND_HALF_UP))
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

    public static String getRandomCode(int count) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < count; ++i) {
            sb.append(NumberUtils.random(0, 9));
        }
        return sb.toString();
    }

    public static String getRandomID(int length) {
        Random random = new Random();
        char[] cs = new char[length];
        for(int i = 0; i < cs.length; ++i) {
            cs[i] = digits[random.nextInt(digits.length)];
        }
        return new String(cs);
    }

    private static int random(int min, int max) {
        return (int)((double)(max - min + 1) * Math.random()) + min;
    }

    public static void main(String[] args) throws ParseException {
        String str = "29%";
        System.out.println(convertDecimal(str));
        BigDecimal gratuity = getGratuity(new BigDecimal("10.00"), str);
        System.out.println(gratuity);
    }
}
