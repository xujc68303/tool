package com.xjc.tool.secure;

import cn.hutool.crypto.asymmetric.AsymmetricCrypto;
import cn.hutool.crypto.asymmetric.KeyType;
import sun.misc.BASE64Decoder;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @Description TokenUtil
 * @Date 2021/7/5 15:25
 * @Created by Xujc
 */
public class TokenUtil {

    private final String token;

    private final AsymmetricCrypto asymmetricCrypto;

    public TokenUtil(String token) {
        this.token = token;
        asymmetricCrypto = new AsymmetricCrypto("RSA", getRSAPrivateKeyBybase64(token), getRSAPublidKeyBybase64(token));
    }

    public String getPublicKey() {
        return asymmetricCrypto.encryptBase64(this.token, KeyType.PublicKey);
    }

    public String getPrivateKey() {
        return asymmetricCrypto.encryptBase64(this.token, KeyType.PrivateKey);
    }

    public String decryptPrivate(String decryStr) {
        return asymmetricCrypto.decryptStr(decryStr, KeyType.PublicKey);
    }

    public String decryPtPublic(String decryStr) {
        return asymmetricCrypto.decryptStr(decryStr, KeyType.PrivateKey);
    }

    private RSAPublicKey getRSAPublidKeyBybase64(String base64s) {
        RSAPublicKey publicKey = null;
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec((new BASE64Decoder()).decodeBuffer(base64s));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (Exception var1) {

        }
        return publicKey;
    }

    private RSAPrivateKey getRSAPrivateKeyBybase64(String base64s) {
        RSAPrivateKey privateKey = null;
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec((new BASE64Decoder()).decodeBuffer(base64s));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (Exception var2) {
        }
        return privateKey;
    }

    public static void main(String[] args) {
        TokenUtil tokenUtil = new TokenUtil("xjc");
        String privateKey = tokenUtil.getPrivateKey();
        System.out.println(privateKey);
        String s = tokenUtil.decryptPrivate(privateKey);
        System.out.println(s);
        String publicKey = tokenUtil.getPublicKey();
        System.out.println(publicKey);
        String s1 = tokenUtil.decryPtPublic(publicKey);
        System.out.println(s1);
    }

}
