package com.boyuanitsm.zhetengba.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by wangbin on 16/4/28.
 */
public class ZtbUtils {
    /**
     * 获取手机屏幕高
     *
     * @param activity activity
     */
    public static int getScreenHeight(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        return metrics.heightPixels;

    }
}
