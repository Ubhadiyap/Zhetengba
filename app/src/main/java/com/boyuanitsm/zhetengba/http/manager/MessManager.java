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
        OkHttpManager.getInstance().doPost(IZtbUrl.FINDUSERBYPHONE_URL,map,callback);
    }

    /**
     * 同意添加好友
     * @param friendId
     * @param callback
     */
    public void aggreeFriend(String friendId,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("friendId",friendId);
        OkHttpManager.getInstance().doPost(IZtbUrl.ADD_FRIEND_URL,map,callback);
    }

    /**
     * 删除好友
     * @param friendId
     * @param callback
     */
    public void deleteFriend(String friendId,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("friendId",friendId);
        OkHttpManager.getInstance().doPost(IZtbUrl.DELETE_FRIEND_URL,map,callback);
    }

    /**
     * 获取好友列表
     * @param callback
     */
    public void getFriends(String page, String rows,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("page",page+"");
        map.put("rows",rows+"");
        OkHttpManager.getInstance().doPost(IZtbUrl.FRIEND_LIST_URL,map,callback);
    }

    /**
     * 添加已经注册好友
     * @param friendId
     * @param callback
     */
    public void addFriendFromContact(String friendId,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("friendId",friendId);
        OkHttpManager.getInstance().doPost(IZtbUrl.ADD_FRIEND_CONTACT,map,callback);
    }

    /**
     * 修改昵称
     * @param newNickName
     * @param callback
     */
    public void updateNickName(String newNickName,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("newNickName",newNickName);
        OkHttpManager.getInstance().doPost(IZtbUrl.UPDATE_NICKNAME, map, callback);
    }

    /**
     * 创建群聊
     * @param timeLength
     * @param personIds
     * @param callback
     */
    public void createGroup(String timeLength,String personIds,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("remark",timeLength);
        map.put("personIds",personIds);
        OkHttpManager.getInstance().doPost(IZtbUrl.ADD_GROUP_URL, map, callback);
    }

    /**
     * 获取群成员列表
     * @param groupId
     * @param callback
     */
    public void getGroupMember(String groupId,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("hgroupid",groupId);
        OkHttpManager.getInstance().doPost(IZtbUrl.GET_GROUP_MEMBER_URL, map, callback);
    }

    /**
     * 移除群成员
     * @param personIds 若当前登录用户为群主,当前用户的ID赋值给参数personIds,则为解散群
     * @param callback
     */
    public void removeGroupPerson(String groupId,String personIds,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("hgroupid",groupId);
        map.put("personIds",personIds);
        OkHttpManager.getInstance().doPost(IZtbUrl.REMOVE_GROUP_PERSON_URL,map,callback);
    }

    /**
     * 退出群聊
     * @param groupId
     * @param callback
     */
    public void exitGroup(String groupId,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("hgroupid",groupId);
        OkHttpManager.getInstance().doPost(IZtbUrl.EXIT_GROUP_URL, map, callback);
    }

    /**
     * 删除好友
     * @param friendId
     * @param callback
     */
    public void deleteFriends(String friendId,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("friendId",friendId);
        OkHttpManager.getInstance().doPost(IZtbUrl.REMOVE_FRIEND_URL, map, callback);
    }

    /**
     * 增加群成员
     * @param groupId
     * @param personIds
     * @param callback
     */
    public void addGroupMember(String groupId,String personIds,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("groupId",groupId);
        map.put("personIds",personIds);
        OkHttpManager.getInstance().doPost(IZtbUrl.ADD_GROUP_MEMBER_URL,map,callback);
    }

    /**
     * 查找群消息
     * @param hgroupid
     */
    public void findGroupInfo(String hgroupid,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("hgroupid",hgroupid);
        OkHttpManager.getInstance().doPost(IZtbUrl.GROUP_INFO, map, callback);
    }

    /**
     * 通过UserId获取用户信息
     * @param personId
     */
    public void findUserByHId(String personId,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("personId",personId);
        OkHttpManager.getInstance().doPost(IZtbUrl.FIND_USER_URL,map,callback);
    }



    /**
     * 设置里面添加好友需要验证
     * @param callback
     */
    public void isCheck(ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        OkHttpManager.getInstance().doPost(IZtbUrl.ISCHECKED_URL,map,callback);
    }

    /**
     * 从首页点击头像进来点击加为好友时调的获取用户信息接口
     * @param personId
     * @param callback
     */
    public void findUserIcon(String personId,ResultCallback callback){
        Map<String,String>map=new HashMap();
        map.put("personId",personId);
        OkHttpManager.getInstance().doPost(IZtbUrl.FINDUSERICON_URL,map,callback);

    }

    /**
     * 修改群名称
     * @param hgroupid
     * @param newGroupName
     */
    public void updateGName(String hgroupid,String newGroupName,ResultCallback callback){
        Map<String,String>map=new HashMap();
        map.put("hgroupid",hgroupid);
        map.put("newGroupName",newGroupName);
        OkHttpManager.getInstance().doPost(IZtbUrl.UPDATE_GROUPNAME_URL, map, callback);
    }
    /**
     * 修改备注
     * @param friendId
     * @param remark
     */
    public void gaiBz(String friendId,String remark,ResultCallback callback){
        Map<String,String>map=new HashMap();
        map.put("friendId",friendId);
        map.put("remark",remark);
        OkHttpManager.getInstance().doPost(IZtbUrl.REMARK_URL, map, callback);
    }
    /**
     * 获取活动列表
     * @param
     * @param
     */
    public void getActivity(ResultCallback callback){
        Map<String,String>map=new HashMap();
        OkHttpManager.getInstance().doPost(IZtbUrl.ACTIVITY_URL, map, callback);
    }

    /**
     * 获取我的群聊列表
     * @param callback
     */
    public void getGroup(ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        OkHttpManager.getInstance().doPost(IZtbUrl.GROUP_LIST,map,callback);
    }
}
