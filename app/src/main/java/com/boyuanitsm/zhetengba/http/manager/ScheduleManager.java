package com.boyuanitsm.zhetengba.http.manager;

import android.text.TextUtils;

import com.boyuanitsm.zhetengba.bean.ScheduleInfo;
import com.boyuanitsm.zhetengba.bean.SimpleInfo;
import com.boyuanitsm.zhetengba.http.IZtbUrl;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.utils.Uitls;

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
     * @param
     * @param callback
     */
    public void addActivity(SimpleInfo simpleInfo, ResultCallback callback) {
        Map<String,String> params=new HashMap<>();
        params= Uitls.obj2Map(simpleInfo);
        doPost(IZtbUrl.ADD_ACTIVITY, params, callback);
    }

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
    public void getActivityList(int page, int rows, ResultCallback callback) {
        Map<String, String> params = new HashMap<>();
        params.put("page", page+"");
        params.put("rows", rows+"");
        doPost(IZtbUrl.ACTIVITY_LIST, params, callback);
    }

    /**
     * 活动详情
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

    /**
     * 6.1
     * 所有的活动标签 接口返回:ResponseEntity(集合 List ActivityLabel) 响应信息
     * @param callback
     */
    public void getAllActivityLabel(ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        doPost(IZtbUrl.ACTIVITY_LABEL_URL,params,callback);
    }

    /**
     * userId - 用户id 注：userId为空时查询个人兴趣标签；userId不为空时，查询他人兴趣标签（活动、档期列表点击头像查看信息）
     * 返回:ResponseEntity(集合 List UserInterest) 响应信息
     * @param
     * @param callback
     */
    public void findMyLabelListMoreByUserId(ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        doPost(IZtbUrl.MY_INTEREST_URL,params,callback);
    }
    /**
     * userId - 用户id 注：userId为空时查询个人兴趣标签；userId不为空时，查询他人兴趣标签（活动、档期列表点击头像查看信息）
     * 返回:ResponseEntity(集合 List UserInterest) 响应信息
     * @param
     * @param callback
     */
    public void findOtherListMoreByUserId(String userId,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        doPost(IZtbUrl.MY_INTEREST_URL,params,callback);
    }
    /**
     * 档期轮播图,返回LabelBanner集合
     * @param callback
     */
    public void getScheduleBanner(ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        doPost(IZtbUrl.BANNER_CAN_URL,params,callback);
    }

    /**
     * 档期列表，返回Schedule集合
     * @param page
     * @param rows
     * @param callback
     */
    public void getScheduleList(int page,int rows,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("page",page+"");
        params.put("rows",rows+"");
        doPost(IZtbUrl.SCHEDULE_LIST_URL,params,callback);
    }

    /**
     * 点击约她判断匹配时间
     * @param scheduleId
     * @param callback
     */
    public void findMatchingActivities(String scheduleId,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("scheduleId",scheduleId);
        doPost(IZtbUrl.FINDMATCHING_URL,params,callback);
    }

    /**
     * 关注档期 ，返回String响应状态
     * @param scheduleId
     * @param callback
     */
    public void getScheduleCollection(String scheduleId ,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("scheduleId",scheduleId);
        doPost(IZtbUrl.SCHEDULE_COLLECTION_URL,params,callback);
    }

    /**
     * 约她，返回String响应状态
     * @param scheduleId
     * @param callback
     */
    public void getAbout(String scheduleId,String activityId,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("scheduleId",scheduleId);
        params.put("activityId",activityId);
        doPost(IZtbUrl.ABOUT_URL,params,callback);
    }

    /**
     * 邀请消息接口
     * @param type
     * @param page
     * @param rows
     * @param callback
     */
    public void findMyInviteMsg(String type,int page,int rows,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("type",type);
        params.put("page",page+"");
        params.put("rows",rows+"");
        doPost(IZtbUrl.INVITE_MSG_URL,params,callback);
    }
    /**
     * 档期显示（好友/全部）接口,返回Schedule集合
     * state 1是全部，0是好友；
     * @param page
     * @param rows
     * @param state
     * @param callback
     */
    public void getScheduleFriend(int page,int rows,String state,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("page",page+"");
        params.put("rows",rows+"");
        params.put("state",state);
        doPost(IZtbUrl.SCHEDULE_FRIEND_URL,params,callback);
    }

    /**
     * 活动指定谁看，通知谁，返回响应状态（String)
     * 多个id用，隔开如：userIds="aaa,bbb"
     * @param userIds
     * @param callback
     */
    public void getActivityNotice(String userIds,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("userIds",userIds);
        doPost(IZtbUrl.ACTIVITY_NOTICE_URL,params,callback);
    }
    /**
     * 活动指定谁不能看，返回响应状态（String)
     * 多个id用，隔开如：userIds="aaa,bbb"
     * @param userIds
     * @param callback
     */
    public void getActivityNotice(String activityId,String userIds,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("activityId",activityId);
        params.put("userIds",userIds);
        doPost(IZtbUrl.ACTIVITY_INVISIBLE_URL,params,callback);
    }

    /**
     * 活动仅好友可见，返回响应状态（String)
     * 返回: ResponseEntity 响应状态
     * 多个id用，隔开如：userIds="aaa,bbb"
     * @param activityId
     * @param callback
     */
    public void modifyActivityFriendSee(String activityId,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("activityId",activityId);
        doPost(IZtbUrl.ACTIVITY_FRIEND_URL,params,callback);
    }
    /**
     * 档期指定谁看，通知谁，返回响应状态（String)
     * 多个id用，隔开如：userIds="aaa,bbb"
     * @param userIds
     * @param callback
     */
    public void getScheduleNotice(String userIds,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("userIds",userIds);
        doPost(IZtbUrl.SHEDULE_NOTICE_URL,params,callback);
    }
    /**
     * 档期指定谁不能看，返回响应状态（String)
     * 多个id用，隔开如：userIds="aaa,bbb"
     * @param userIds
     * @param callback
     */
    public void getScheduleNotice(String activityId,String userIds,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("activityId",activityId);
        params.put("userIds",userIds);
        doPost(IZtbUrl.SHEDULE_INVISIBLE_URL,params,callback);
    }

    /**
     * 档期仅好友可见，返回响应状态（String)
     * 返回: ResponseEntity 响应状态
     * 多个id用，隔开如：userIds="aaa,bbb"
     * @param scheduleId
     * @param callback
     */
    public void modifyScheduleFriendSee(String scheduleId,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("scheduleId",scheduleId);
        doPost(IZtbUrl.SHEDULE_FRIEND_URL,params,callback);
    }

    /**
     * 自己发布的活动接口
     * 集合 List Activity
     * @param callback
     */
    public void findMyActivity(ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        doPost(IZtbUrl.MY_ACTIVITY_URL,params,callback);
    }

    /**
     * 取消活动接口 （只能取消自己发布的活动）
     * 返回：ResponseEntity 响应状态
     * @param activityId
     * @param callback
     */
    public void removeActivity(String activityId,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("activityId",activityId);
        doPost(IZtbUrl.REMOVE_ACTIVITY_URL,params,callback);
    }

    /**
     * 取消档期接口 （只能取消自己发布的活动）
     * 返回：ResponseEntity 响应状态
     * @param schuldelId
     * @param callback
     */
    public void removeSchuldel(String schuldelId,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("scheduleId",schuldelId);
        doPost(IZtbUrl.REMOVE_SHEDULE_URL,params,callback);
    }
    /**
     * 发布档期
     * schedule - 档期实体 [NOT NULL (标签ID:labelId,开始时间 ：startTime，结束时间：endTime);默认值(可见类型:scheduleVisibility 默认1(1全部可见2好友可见) )]
     * @param schedule
     * @param callback
     */
    public void addSchedule(ScheduleInfo schedule,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params= Uitls.obj2Map(schedule);
        doPost(IZtbUrl.ADD_SHEDULE_URL, params, callback);
    }

    /**
     * 发布活动推送接口
     * 参数:activityId - 活动主键ID
     * 返回:ResponseEntity 响应信息
     * @param activityId
     * @param callback
     */

    public void pushActivityMessage(String activityId,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("activityId",activityId);
        doPost(IZtbUrl.ACTIVITY_MESSAGE_URL,params,callback);
    }

    /**
     * 发布档期推送接口
     * 参数:scheduleId - 档期主键Id
     * 返回:ResponseEntity 响应信息
     * @param scheduleId
     * @param callback
     */

    public void pushScheduleMessage(String scheduleId,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("scheduleId",scheduleId);
        doPost(IZtbUrl.SHEDULE_MESSAGE_URL,params,callback);
    }

    /**
     * 取消收藏接口
     * @param collectionId
     * @param callback
     */
    public void removeCollection(String collectionId,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("collectionId",collectionId);
        doPost(IZtbUrl.REMOVECOLLECTION_URL,params,callback);
    }

    /**
     * 获取事件（时间轴接口）
     * @param callback
     */
    public void findHistoryMessageListByYM(String yearMonth,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("yearMonth",yearMonth);
        doPost(IZtbUrl.HISTORYMESSAGE_URL, params,callback);
    }

    /**
     * 取消参加活动
     * @param activityId
     * @param callback
     */
    public void cancelActivity(String activityId,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("activityId",activityId);
        doPost(IZtbUrl.CANCEL_ACTIVITY_URL,params,callback);
    }

    /**
     * 获取个人主页信息
     * @param userId
     * @param callback
     */
    public void getPersonalMain(String userId,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("userId",userId);
        doPost(IZtbUrl.PERSONAL_HOME_PAGE_URL,params,callback);
    }

    /**
     * 添加换新群组
     * @param activityId
     * @param callback
     */
    public void addHXGroup(String activityId,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("activityId",activityId);
        doPost(IZtbUrl.ADD_HGROUP_URL,params,callback);
    }

    /**
     * 移除群组
     * @param activityId
     * @param callback
     */
    public void deleGroup(String activityId,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("activityId",activityId);
        doPost(IZtbUrl.DELET_GROUP_URL,params,callback);
    }
    /**
     * 查询我的兴趣标签
     * @param userId
     * @param limitNum
     * @param callback
     */
    public void selectMyLabels(String userId ,int limitNum ,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        if(!TextUtils.isEmpty(userId)){
            params.put("userId",userId );
        }
        if(!TextUtils.isEmpty(limitNum+"")){
            params.put("limitNum",limitNum+"");
        }
        doPost(IZtbUrl.MYLABELLIST_URL,params,callback);
    }

    /**
     * t同意加入活动接口
     * @param activityId
     * @param scheduleId
     * @param callback
     */
    public void agreeActivity(String activityId, String scheduleId,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("activityId",activityId);
        params.put("scheduleId",scheduleId);
        doPost(IZtbUrl.AGREE_ACTIVITY_URL,params,callback);

    }

    /**
     * 拒绝加入活动接口
     * @param activityId
     * @param scheduleId
     * @param callback
     */
    public void refuseActivity(String activityId, String scheduleId,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("activityId",activityId);
        params.put("scheduleId",scheduleId);
        doPost(IZtbUrl.REFUSE_ACTIVITY_URL,params,callback);
    }
}
