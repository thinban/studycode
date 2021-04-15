package com.example.thinbanrest.utils;

import cn.hutool.crypto.Mode;
import cn.hutool.crypto.Padding;
import cn.hutool.crypto.symmetric.AES;
import com.example.thinbanrest.aop.EncryptProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.AlgorithmParameters;
import java.security.Security;

/**
 * app专用
 * AES的加密和解密
 * <p>
 * 前端写法：
 * <script src="https://cdn.bootcss.com/crypto-js/3.1.9-1/crypto-js.min.js"></script>
 * let iv = '0102030405060708';
 * <p>
 * function encrypt(word){
 * let keys = CryptoJS.enc.Utf8.parse(key);
 * let ivs= CryptoJS.enc.Utf8.parse(iv);
 * let srcs = CryptoJS.enc.Utf8.parse(word);
 * encrypted = CryptoJS.AES.encrypt(srcs, keys, {
 * iv: ivs,
 * mode: CryptoJS.mode.CBC,
 * padding: CryptoJS.pad.Pkcs7
 * });
 * <p>
 * return encrypted.ciphertext.toString();
 * }
 * <p>
 * function decrypt(word){
 * let encryptedHexStr = CryptoJS.enc.Hex.parse(word);
 * let src = CryptoJS.enc.Base64.stringify(encryptedHexStr);
 * let keys = CryptoJS.enc.Utf8.parse(key);
 * let ivs= CryptoJS.enc.Utf8.parse(iv);
 * let decrypt = CryptoJS.AES.decrypt(src, keys, {
 * iv: ivs,
 * mode: CryptoJS.mode.CBC,
 * padding: CryptoJS.pad.Pkcs7
 * });
 * <p>
 * let decryptedStr = decrypt.toString(CryptoJS.enc.Utf8);
 * return decryptedStr.toString();
 * }
 *
 * @author thinban
 */
@Slf4j
public class AppAesUtils {

    /**
     * aes解密
     *
     * @param encrypt 内容
     */
    public static String aesDecrypt(String encrypt) {
        AES aes = AesSingletonHolder.aesSingleton;
        return aes.decryptStr(encrypt);
    }

    /**
     * AES加密
     *
     * @param content 待加密的内容
     */
    public static String aesEncrypt(String content) {
        AES aes = AesSingletonHolder.aesSingleton;
        return aes.encryptHex(content);
    }

    private static Cipher cipher;

    static {
        try {
            Security.addProvider(new BouncyCastleProvider());
            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        } catch (Exception e) {
            log.error("微信用户数据解密失败:%s", e.getMessage());
        }
    }

    /**
     * 微信用户数据解密
     * <p>
     * https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/signature.html
     * 对称解密使用的算法为 AES-128-CBC，数据采用PKCS#7填充。
     * 对称解密的目标密文为 Base64_Decode(encryptedData)。
     * 对称解密秘钥 aeskey = Base64_Decode(session_key), aeskey 是16字节。
     * 对称解密算法初始向量 为Base64_Decode(iv)，其中iv由数据接口返回。
     *
     * @param encryptedData
     * @param sessionKey
     * @param iv
     * @return
     */
    public static String miniWeixinDecode(String encryptedData, String sessionKey, String iv) {
        if (StringUtils.isAnyBlank(encryptedData, sessionKey, iv)) {
            log.error("微信用户数据解密，参数为空   encryptedData:%s-sessionKey:%s- iv:%s", encryptedData, sessionKey, iv);
            return null;
        }
        try {
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] encryptedByte = base64Decoder.decodeBuffer(encryptedData);
            byte[] sessionKeyByte = base64Decoder.decodeBuffer(sessionKey);
            byte[] ivByte = base64Decoder.decodeBuffer(iv);
            SecretKeySpec skeySpec = new SecretKeySpec(sessionKeyByte, "AES");
            AlgorithmParameters params = AlgorithmParameters.getInstance("AES");
            params.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, params);
            byte[] original = cipher.doFinal(encryptedByte);
            return new String(original);
        } catch (Exception e) {
            log.error("微信用户数据解密失败:%s", e.getMessage());
        }
        return null;
    }

    private static class AesSingletonHolder {
        /**
         * 模式
         */
        private static final Mode MODE = Mode.CBC;
        /**
         * 对齐方式
         */
        private static final Padding PADDING = Padding.PKCS5Padding;
        /**
         * 偏移量
         */
        private static final String IV = "0102030405060708";
        /**
         * aes单例
         */
        public static AES aesSingleton;

        static {
            aesSingleton = new AES(MODE, PADDING, EncryptProperties.key.getBytes(), IV.getBytes());
        }
    }

    public static void main(String[] args) {
        String s = aesEncrypt("{\"id\":1,\"firstname\":\"demoData\",\"lastname\":\"demoData\"}");
        System.out.println(s);
        System.out.println(aesDecrypt(s));
    }
}