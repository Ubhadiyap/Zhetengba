package com.boyuanitsm.zhetengba.http.manager;

import com.boyuanitsm.zhetengba.bean.SimpleInfo;
import com.boyuanitsm.zhetengba.http.IZtbUrl;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 档期管理类
 * Created by xiaoke on 2016/5/31.
 */
public class ScheduleManager extends RequestManager {


    /***
     * 发布活动
     *
     * @param simpleInfo
     * @param callback
     */
//    public void addActivity(String activityTheme,String startTime,String endTime,String inviteNumber,String labelId,String activityVisibility ,String icon ,ResultCallback callback){
//        Map<String,String> params=new HashMap<>();
//        params.put("activityTheme",activityTheme);
//        params.put("startTime",startTime);
//        params.put("endTime",endTime);
//        params.put("inviteNumber",inviteNumber);
//        params.put("labelId",labelId);
//        params.put("activityVisibility",activityVisibility);
//        params.put("icon",icon);
//        doPost(IZtbUrl.ADD_ACTIVITY,params,callback);
//    }
//    public void addActivity(SimpleInfo simpleInfo, ResultCallback callback) {
//        Map<String, String> params = new HashMap<>();
//        params.put("activity", simpleInfo + "");
////        params.put("startTime",startTime);
////        params.put("endTime",endTime);
////        params.put("inviteNumber",inviteNumber);
////        params.put("labelId",labelId);
////        params.put("activityVisibility",activityVisibility);
////        params.put("icon",icon);
//        doPost(IZtbUrl.ADD_ACTIVITY, params, callback);
//    }

    /**
     * getBanner
     * 获取轮播图片
     *
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
     *
     * @param activityId
     * @param callback
     */
    public void getActivityDetials(String activityId, ResultCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("activityId", activityId);
        doPost(IZtbUrl.ACTIVITY_DETIALS, params, callback);
    }

    /**
     * 关注活动
     *
     * @param activityId
     * @param callback
     */
    public void getActivityCollection(String activityId, ResultCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("activityId", activityId);
        doPost(IZtbUrl.ACTIVITY_COLLECTION_URL, params, callback);

    }

    /**
     * 响应参加活动接口
     *
     * @param activityId
     * @param callback
     */
    public void getRespondActivity(String activityId, ResultCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("activityId", activityId);
        doPost(IZtbUrl.RESPOND_ACTIVITY_URL, params, callback);
    }

    /***
     * 显示好友/全部活动接口
     * @param page
     * @param rows
     * @param state
     */
    public void getFriendOrAllActivity(int page, int rows, String state,ResultCallback callback) {
        Map<String,String> params=new HashMap<>();
        params.put("page",page+"");
        params.put("rows",rows+"");
        params.put("state",state);
        doPost(IZtbUrl.FRIEND_ALL_URL,params,callback);
    }

    /**
     * 全部兴趣/档期标签
     * 0是兴趣，1是档期
     * @param dictType
     * @param callback
     */
    public void getIntrestLabelList(String dictType,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("dictType",dictType);
        doPost(IZtbUrl.INTREST_LABEL_URL,params,callback);
    }

    /**
     * 添加兴趣标签
     * @param labelIds
     * @param callback
     */
    public void addInterestLabel(String labelIds,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("labelIds", labelIds);
        doPost(IZtbUrl.ADD_INTREST_LABEL, params, callback);
    }
}
