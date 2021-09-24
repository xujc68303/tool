package com.xjc.tool.string;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

/**
 * @Author jiachenxu
 * @Date 2021/6/27
 * @Descripetion
 */
public class SecureUtilTest {

    private static final byte[] KEY = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
    private static final String SECRET_KEY = new BASE64Encoder().encodeBuffer(KEY);


    public static void main(String[] args) {
//        RSA rsa = SecureUtil.rsa();

//        String str = rsa.encryptBcd("微信token", KeyType.PublicKey);
//        System.out.println(str);
//
//        String privateStr = rsa.decryptStr(str, KeyType.PrivateKey);
//        System.out.println(privateStr);
        String token = encrypt("微信token");
        System.out.println(token);
        String decode = decode(token);
        System.out.println(decode);


    }


    public static String encrypt(String info) {
        byte[] key = new byte[0];
        try {
            key = new BASE64Decoder().decodeBuffer(SECRET_KEY);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SecureUtil.aes(key).encryptHex(info);
    }

    public static String decode(String encrypt) {
        byte[] key = new byte[0];
        try {
            key = new BASE64Decoder().decodeBuffer(SECRET_KEY);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SecureUtil.aes(key).decryptStr(encrypt);
    }


}
