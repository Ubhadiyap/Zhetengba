package com.boyuanitsm.zhetengba.utils;


import android.text.TextUtils;



import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;


/**
 * 编码格式UTF-8 转码
 *
 * @author Hui.Li
 */
public class EmojUtils {

    public static final String ENCODING = "UTF-8";
    /**
     * 服务器的字符解码
     *
     * @param str 字符串
     * @return 字符串
     */
    public static String decoder(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }

        try {
            return URLDecoder.decode(str, ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 传入服务器的字符需处理
     *
     * @param str 字符串
     * @return 字符串
     */
    public static String encoder(String str) {

        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {

            return URLEncoder.encode(str, ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;

    }
}
