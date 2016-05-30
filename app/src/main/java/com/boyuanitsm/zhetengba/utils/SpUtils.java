package com.boyuanitsm.zhetengba.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author wangbin
 */
public class SpUtils {
    private final static String ZTB_SP="ztb_sp";

    private final static String ISFIRST = "is_first";

    private final static String CHAT_READ="chat_read";//阅后即焚用

    private final static String SCREEN_WITH="screen_with";



    public static SharedPreferences getSp(Context context) {
        return context.getSharedPreferences(ZTB_SP, Context.MODE_PRIVATE);
    }



    /**
     * 是否第一次进入app
     *
     * @param context
     * @param isFirst
     */
    public static void setIsFirst(Context context, boolean isFirst) {
        getSp(context).edit().putBoolean(ISFIRST, isFirst).commit();
    }

    public static boolean getIsFirst(Context context) {
        return getSp(context).getBoolean(ISFIRST, true);
    }


    public static void setRead(Context context, boolean isReadDestory){
        getSp(context).edit().putBoolean(CHAT_READ, isReadDestory).commit();
    }

    /**
     * 获取是否阅读后即焚，默认false
     * @param context
     * @return
     */
    public static boolean getIsReadDestory(Context context) {
        return getSp(context).getBoolean(CHAT_READ, false);
    }


    public static void setScreenWith(Context context,int screenWith){
        getSp(context).edit().putInt(SCREEN_WITH,screenWith).commit();
    }

    public static int getScreenWith(Context context){
        return getSp(context).getInt(SCREEN_WITH,0);
    }
}
