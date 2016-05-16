package com.boyuanitsm.zhetengba.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author wangbin
 */
public class SpUtils {

    private final static String PARKING = "PARKING_SP";
    private final static String CACHE = "cache";
    private final static String DIALOG = "dialog";

    private final static String KONG = "is_kong";//空车位
    private final static String PAY = "is_pay";//在线支付
    private final static String ORDER = "is_order";//预约
    private final static String CHARGING = "is_charging";//充电
    private final static String STAGGER = "is_stagger";//错时停车
    private final static String PARKLX = "park_lx";//停车场类型

    private final static String ISFIRST = "is_first";

    private final static String SIGNID = "sign_id";
    // 首页广告缓存
    private final static String ADVER = "adver";
    // 首页底部公告栏缓存
    private final static String ANNO = "anno";
    // 城市
    private final static String CITY = "city";

    public static SharedPreferences getCacheSp(Context context) {
        return context.getSharedPreferences(CACHE, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getSp(Context context) {
        return context.getSharedPreferences(PARKING, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getDialogSp(Context context) {
        return context.getSharedPreferences(DIALOG, Context.MODE_PRIVATE);
    }

    /**
     * 是否第一次进入app
     *
     * @param context
     * @param isFirst
     */
    public static void setIsFirst(Context context, boolean isFirst) {
        getCacheSp(context).edit().putBoolean(ISFIRST, isFirst).commit();
    }

    public static boolean getIsFirst(Context context) {
        return getCacheSp(context).getBoolean(ISFIRST, true);
    }

    /**
     * 是否选择空车位
     *
     * @param context
     * @param isFirst
     */
    public static void setIsKong(Context context, boolean isFirst) {
        getDialogSp(context).edit().putBoolean(KONG, isFirst).commit();
    }

    public static boolean getIsKong(Context context) {
        return getDialogSp(context).getBoolean(KONG, false);
    }

    /**
     * 是否选择在线支付
     *
     * @param context
     * @param isFirst
     */
    public static void setIsPay(Context context, boolean isFirst) {
        getDialogSp(context).edit().putBoolean(PAY, isFirst).commit();
    }

    public static boolean getIsPay(Context context) {
        return getDialogSp(context).getBoolean(PAY, false);
    }

    /**
     * 是否可预约停车
     *
     * @param context
     * @param isFirst
     */
    public static void setIsOrder(Context context, boolean isFirst) {
        getDialogSp(context).edit().putBoolean(ORDER, isFirst).commit();
    }

    public static boolean getIsOrder(Context context) {
        return getDialogSp(context).getBoolean(ORDER, false);
    }

    /**
     * 是否可充电
     *
     * @param context
     * @param isFirst
     */
    public static void setIsCharging(Context context, boolean isFirst) {
        getDialogSp(context).edit().putBoolean(CHARGING, isFirst).commit();
    }

    public static boolean getIsCharging(Context context) {
        return getDialogSp(context).getBoolean(CHARGING, false);
    }

    /**
     * 是否可错时停车
     *
     * @param context
     * @param isFirst
     */
    public static void setIsStagger(Context context, boolean isFirst) {
        getDialogSp(context).edit().putBoolean(STAGGER, isFirst).commit();
    }

    public static boolean getIsStagger(Context context) {
        return getDialogSp(context).getBoolean(STAGGER, false);
    }

    /**
     * 选择停车场类型
     *
     * @param context
     * @param isFirst
     */
    public static void setParklx(Context context, String isFirst) {
        getDialogSp(context).edit().putString(PARKLX, isFirst).commit();
    }

    public static String getParklx(Context context) {
        return getDialogSp(context).getString(PARKLX, "");
    }

    /**
     * 保存当前的城市
     *
     * @param context
     * @param city
     */
    public static void setCity(Context context, String city) {
        getCacheSp(context).edit().putString(CITY, city).commit();
    }

    /**
     * 获取上一次的城市
     *
     * @param context
     */
    public static String getCity(Context context) {
        return getCacheSp(context).getString(CITY, "");
    }

    /**
     * 保存用户signid
     *
     * @param context
     * @param sign_id
     */
    public static void setSignId(Context context, String sign_id) {
        getSp(context).edit().putString(SIGNID, sign_id).commit();
    }

    public static String getSignId(Context context) {
        return getSp(context).getString(SIGNID, "");
    }

    /**
     * 存储首页广告
     *
     * @param context
     * @param adver
     */
    public static void setMainAdver(Context context, String adver) {
        getSp(context).edit().putString(ADVER, adver).commit();
    }

    public static String getMainAdver(Context context) {
        return getSp(context).getString(ADVER, "");
    }

    /**
     * 存储首页底部公告栏缓存
     *
     * @param context
     * @param anno
     */
    public static void setAnno(Context context, String anno) {
        getSp(context).edit().putString(ANNO, anno).commit();
    }

    /**
     * 获取首页底部公告栏缓存
     *
     * @param context
     * @return
     */
    public static String getAnno(Context context) {
        return getSp(context).getString(ANNO, "");
    }

    /**
     * 清除
     *
     * @param context
     */
    public static void clearData(Context context) {
        getSp(context).edit().clear().commit();
    }

    public static void clearDialog(Context context) {
        getDialogSp(context).edit().clear().commit();
    }

    public static void setScreenWith(Context context, int with) {
        getSp(context).edit().putInt("with", with).commit();
    }

    public static int getScreenWith(Context context) {
        return getSp(context).getInt("with", 720);
    }

}
