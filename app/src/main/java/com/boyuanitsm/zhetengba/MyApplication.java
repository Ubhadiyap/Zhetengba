package com.boyuanitsm.zhetengba;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.boyuanitsm.zhetengba.chat.DemoHelper;

/**
 * Created by wangbin on 16/5/9.
 */
public class MyApplication extends Application{
    public static Context applicationContext;
    private static MyApplication instance;
    // login user name
    public final String PREF_USERNAME = "username";

    /**
     * 当前用户nickname,为了苹果推送不是userid而是昵称
     */
    public static String currentUserNick = "";
    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        applicationContext = this;
        instance = this;

        //init demo helper
        DemoHelper.getInstance().init(applicationContext);

    }

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
