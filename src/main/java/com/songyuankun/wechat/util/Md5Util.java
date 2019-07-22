package com.songyuankun.wechat.util;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author songyuankun
 */
@Slf4j
public class Md5Util {
    /**
     * 16进制字符
     */
    private static String[] chars = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 将普通字符串用md5加密，并转化为16进制字符串
     */
    public static String stringInMd5(String str) {

        // 消息签名（摘要）
        MessageDigest md5;
        try {
            // 参数代表的是算法名称
            md5 = MessageDigest.getInstance("md5");
            byte[] result = md5.digest(str.getBytes());

            StringBuilder sb = new StringBuilder(32);
            // 将结果转为16进制字符  0~9 A~F
            for (byte x : result) {
                // 一个字节对应两个字符
                // 取得高位
                int h = 0x0f & (x >>> 4);
                // 取得低位
                int l = 0x0f & x;
                sb.append(chars[h]).append(chars[l]);
            }
            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            log.error(e.getLocalizedMessage());
        }
        return "";
    }
}