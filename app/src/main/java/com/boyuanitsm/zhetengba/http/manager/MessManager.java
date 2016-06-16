package com.boyuanitsm.zhetengba.http.manager;

import com.boyuanitsm.zhetengba.http.IZtbUrl;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息管理
 * Created by wangbin on 16/5/31.
 */
public class MessManager extends RequestManager{

    /**
     * 通过手机号搜索用户
     * @param phoneNum
     * @param callback
     */
    public void findUserByPhone(String phoneNum,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("phoneNum",phoneNum);
        doPost(IZtbUrl.FINDUSERBYPHONE_URL,map,callback);
    }

    /**
     * 同意添加好友
     * @param friendId
     * @param callback
     */
    public void aggreeFriend(String friendId,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("friendId",friendId);
        doPost(IZtbUrl.ADD_FRIEND_URL,map,callback);
    }

    /**
     * 删除好友
     * @param friendId
     * @param callback
     */
    public void deleteFriend(String friendId,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("friendId",friendId);
        doPost(IZtbUrl.DELETE_FRIEND_URL,map,callback);
    }

    /**
     * 获取好友列表
     * @param callback
     */
    public void getFriends(ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        doPost(IZtbUrl.FRIEND_LIST_URL,map,callback);
    }

    /**
     * 添加已经注册好友
     * @param friendId
     * @param callback
     */
    public void addFriendFromContact(String friendId,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("friendId",friendId);
        doPost(IZtbUrl.ADD_FRIEND_CONTACT,map,callback);
    }

}
