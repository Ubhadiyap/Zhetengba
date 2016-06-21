package com.boyuanitsm.zhetengba.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by wangbin on 16/3/9.
 */
public class MD5Utils {

    /**
     * md5加密
     *
     * @param str
     *            传入的字符串
     * @return 返回一加密后的字符串
     */
    public static String digest(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] result = digest.digest(str.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                int num = b & 0xff;
                String hex = Integer.toHexString(num);
                // System.out.println(hex);
                if (hex.length() < 2) {
                    sb.append(0);
                }
                sb.append(hex);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            // can't reach
            return "";
        }

    }
}
