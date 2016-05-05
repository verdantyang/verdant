package com.verdant.jtools.common.utils.crypto;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Author: verdant
 * Create: 2016/1/22
 * Func:   密钥算法类
 *     非对称密钥
 *     对称密钥
 *     散列签名: MD5、SHA、HMAC
 */
public class CryptoUtils2 {

    /**
     * MAC算法可选以下多种算法
     *   HmacMD5
     *   HmacSHA1
     *   HmacSHA256
     *   HmacSHA384
     *   HmacSHA512
     */
    public enum Algorithm {

        HmacMD5("HmacSHA256"),
        HmacSHA1("HmacSHA256"),
        HmacSHA256("HmacSHA256"),
        HmacSHA384("HmacSHA256"),
        HmacSHA512("HmacSHA256");

        private String name;
        Algorithm(String name){
            this.name = name;
        }
        private String getName(){
            return this.name;
        }
    }

    /**
     * HMAC 加密
     *
     * @param data 待加密数据
     * @param key 密钥
     * @param algorithm 算法
     * @return
     * @throws Exception
     */
    public static byte[] encryptHMAC(byte[] data, String key, Algorithm algorithm) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key.getBytes("utf-8"), algorithm.getName());
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(data);
    }
}

