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
    /**
     * 检测是否有emoji表情
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否是Emoji
     *
     * @param codePoint 比较的单个字符
     * @return
     */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) ||
                (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000)
                && (codePoint <= 0x10FFFF));
    }
}
