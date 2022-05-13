package com.xjc.tool.yeepay.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class DigestUtil {

    private static final String encodingCharset = "UTF-8";

    public static String hmacSign(String aValue, String aKey) {
        byte[] k_ipad = new byte[64];
        byte[] k_opad = new byte[64];
        byte[] keyb;
        byte[] value;
        try {
            keyb = aKey.getBytes(encodingCharset);
            value = aValue.getBytes(encodingCharset);
        } catch (UnsupportedEncodingException e) {
            keyb = aKey.getBytes();
            value = aValue.getBytes();
        }

        Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
        Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
        for (int i = 0; i < keyb.length; i++) {
            k_ipad[i] = (byte) (keyb[i] ^ 0x36);
            k_opad[i] = (byte) (keyb[i] ^ 0x5c);
        }

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {

            return null;
        }
        md.update(k_ipad);
        md.update(value);
        byte[] dg = md.digest();
        md.reset();
        md.update(k_opad);
        md.update(dg, 0, 16);
        dg = md.digest();
        return toHex(dg);
    }

    public static String toHex(byte[] input) {
        if (input == null){
            return null;
        }
        StringBuffer output = new StringBuffer(input.length * 2);
        for (byte b : input) {
            int current = b & 0xff;
            if (current < 16){
                output.append("0");
            }
            output.append(Integer.toString(current, 16));
        }
        return output.toString();
    }

    public static String getHmac(String[] args, String key) {
        if (args == null || args.length == 0) {
            return (null);
        }
        StringBuffer str = new StringBuffer();
        for (String arg : args) {
            str.append(arg == null ? "" : arg.trim());
        }
        return (hmacSign(str.toString(), key));
    }

    public static boolean verifyCallbackHmac_safe(String[] stringValue, String hmacSafeFromYeePay, String merSecret) {
        if (StringUtils.isBlank(hmacSafeFromYeePay)) {
            return false;
        }
        StringBuffer sourceData = new StringBuffer();
        for (String s : stringValue) {
            if (s != null && !"".equals(s)) {
                sourceData.append(s).append("#");
            }
        }
        String localHmacSafe = DigestUtil.hmacSign(sourceData.deleteCharAt(sourceData.length() - 1).toString(), merSecret);
        if (StringUtils.isBlank(localHmacSafe)) {
            return false;
        }
        return localHmacSafe.equals(hmacSafeFromYeePay);
    }

}
