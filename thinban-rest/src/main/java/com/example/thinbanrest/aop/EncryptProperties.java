package com.example.thinbanrest.aop;

public class EncryptProperties {
    /**
     * AES加密KEY
     */
    public static String key = "keyforaes3334444";

    public static String charset = "UTF-8";

    /**
     * 开启调试模式，调试模式下不进行加解密操作，用于像Swagger这种在线API测试场景
     */
    public static boolean debug = false;

}
