package com.boyuanitsm.zhetengba.http.util;

import android.text.TextUtils;

import com.boyuanitsm.zhetengba.MyApplication;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.utils.SpUtils;

import java.util.HashMap;

import okhttp3.Headers;

/**
 * Created by wangbin on 16/8/23.
 */
public class HttpHeaderHelper {

    /**
     * 获取请求头
     * @param
     * @return
     */
    public static Headers getHeaders() {
        HashMap<String, String> map = new HashMap<>();
        if (!TextUtils.isEmpty(SpUtils.getCookie(MyApplication.getInstance())))
            map.put("Cookie", SpUtils.getCookie(MyApplication.getInstance()));
        if (UserInfoDao.getUser()!=null){
            map.put("userName", UserInfoDao.getUser().getUsername());
        }
        return Headers.of(map);
    }
}
