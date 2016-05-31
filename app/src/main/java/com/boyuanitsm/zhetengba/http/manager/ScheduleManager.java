package com.boyuanitsm.zhetengba.http.manager;

import com.boyuanitsm.zhetengba.http.IZtbUrl;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 档期管理类
 * Created by xiaoke on 2016/5/31.
 */
public class ScheduleManager extends RequestManager {



//    public void addActivity(ResultCallback callback){
//        Map<String,String> params=new HashMap<>();
//    }
    /**
     * getBanner
     *获取轮播图片
     * @param callback
     */
    public void getBanner(ResultCallback callback) {
        Map<String, String> params = new HashMap<>();
        doPost(IZtbUrl.BANNER_URL, params, callback);
    }

    /***
     * 活动列表
     * page页数
     * row行数
     */
    public void getActivityList(String page, String rows, ResultCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("page", page);
        params.put("rows", rows);
        doPost(IZtbUrl.ACTIVITY_LIST, params, callback);
    }

    /**
     * 活动详情
     * @param activityId
     * @param callback
     */
    public void getActivityDetials(String activityId ,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("activityId",activityId);
        doPost(IZtbUrl.ACTIVITY_DETIALS,params,callback);
    }

}
