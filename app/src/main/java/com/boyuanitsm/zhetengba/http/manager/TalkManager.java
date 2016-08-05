package com.boyuanitsm.zhetengba.http.manager;

import android.text.TextUtils;

import com.boyuanitsm.zhetengba.bean.ChannelTalkEntity;
import com.boyuanitsm.zhetengba.bean.CircleEntity;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.IZtbUrl;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.lidroid.xutils.http.client.multipart.content.FileBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 频道/圈子管理
 * Created by wangbin on 16/5/31.
 */
public class TalkManager extends RequestManager{

    /**
     * 建立圈子
     * @param circleEntity
     * @param personIds
     */
    public void addCircle(CircleEntity circleEntity,String personIds,ResultCallback callBack){
        Map<String,String> map=new HashMap<>();
        map.put("circleName",circleEntity.getCircleName());
        if(!TextUtils.isEmpty(circleEntity.getNotice())) {
            map.put("notice", circleEntity.getNotice());
        }
        if(!TextUtils.isEmpty(personIds)){
            map.put("personIds",personIds);
        }
        doPost(IZtbUrl.CREATE_CIRCLE_URL,map,callBack);
    }

    /**
     * 建立圈子邀请好友加入
     * @param circleId
     * @param userIds
     * @param callback
     */

    public void inviteFriendToCircle(String circleId,String userIds,ResultCallback callback){
        Map<String ,String> map=new HashMap<>();
        if (!TextUtils.isEmpty(circleId)){
            map.put("circleId",circleId);
        }
        if (!TextUtils.isEmpty(userIds)){
            map.put("friendIds",userIds);
        }
        doPost(IZtbUrl.INVITE_FRIEND_TOCIRCLE_URL,map,callback);
    }

    /**
     * 我的圈子列表
     * @param page
     * @param rows
     * @param callback
     */
    public void myCircleList(String userId,int page,int rows,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        if(!TextUtils.isEmpty(userId)) {
            map.put("userId", userId);
        }else {
            map.put("userId",UserInfoDao.getUser().getId());
        }
        map.put("page",page+"");
        map.put("rows",rows+"");
        doPost(IZtbUrl.CIRCLE_LIST_URL,map,callback);
    }

    /**
     * 圈子详情
     * @param circleId
     * @param callback
     */
    public void myCircleDetail(String circleId ,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        if(!TextUtils.isEmpty(circleId)) {
            map.put("circleId", circleId);
        }
        doPost(IZtbUrl.CIRCLE_DETAIL_URL,map,callback);
    }

    /**
     * 圈子成员
     * @param circleId
     * @param callback
     */
    public void myCircleMember(String circleId,int page,int rows,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        if(!TextUtils.isEmpty(circleId)) {
            map.put("circleId", circleId);
        }
        map.put("page",page+"");
        map.put("rows",rows+"");
        doPost(IZtbUrl.CIRCLE_MEMBER_URL,map,callback);
    }

    /**
     * 搜索圈子
     * @param circleName
     * @param page
     * @param rows
     * @param callback
     */
    public void searchCircle(String circleName,int page,int rows,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        if(!TextUtils.isEmpty(circleName)) {
            map.put("circleName", circleName);
        }
        map.put("page",page+"");
        map.put("rows",rows+"");
        doPost(IZtbUrl.CIRCLE_SEARCH_URL,map,callback);
    }

    /**
     * 圈主删除人员
     * @param circleId 圈子id
     * @param memberIds 圈子成员id
     * @param callback
     */
    public void removeMember(String circleId ,String memberIds ,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        if(!TextUtils.isEmpty(circleId)){
            map.put("circleId",circleId);
        }
        if(!TextUtils.isEmpty(memberIds)){
            map.put("memberIds",memberIds);
        }
        doPost(IZtbUrl.REMOVE_MEMBER_URL,map,callback);
    }

    /**
     * 圈主发布公告
     * @param circleId
     * @param notice
     * @param callback
     */
    public void addNotice(String circleId ,String notice ,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        if(!TextUtils.isEmpty(circleId)){
            map.put("circleId",circleId);
        }
        if(!TextUtils.isEmpty(notice)){
            map.put("notice",notice);
        }
        doPost(IZtbUrl.ADD_NOTICE_URL,map,callback);
    }

    /**
     * 圈子成员退出圈
     * @param circleId
     * @param callback
     */
    public void removeCircle(String circleId,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        if(!TextUtils.isEmpty(circleId)){
            map.put("circleId",circleId);
        }
        doPost(IZtbUrl.REMOVE_CIRCLE_URL,map,callback);
    }

    /**
     * 圈子说说发布
     * @param circleEntity
     * @param callback
     */
    public void addCircleTalk(CircleEntity circleEntity,String circleId,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        if(circleEntity!=null){
            if(!TextUtils.isEmpty(circleEntity.getTalkContent())){
                map.put("talkContent",circleEntity.getTalkContent());
            }
            if(!TextUtils.isEmpty(circleEntity.getTalkImage())){
                map.put("talkImage",circleEntity.getTalkImage());
            }
        }
        if(!TextUtils.isEmpty(circleId)){
            map.put("circleId",circleId );
        }

        doPost(IZtbUrl.ADD_CIRCLETALK_URL,map,callback);
    }

    /**
     * 所有圈子说说列表
     * @param page
     * @param rows
     * @param callback
     */
    public void getAllCircleTalk(int page,int rows,ResultCallback callback ){
        Map<String,String> map=new HashMap<>();
        map.put("page",page+"");
        map.put("rows",rows+"");
        doPost(IZtbUrl.ALL_CIRCLETALK_URL,map,callback);
    }

    /**
     * 获取单个圈子所有说说列表
     * @param page
     * @param rows
     * @param callback
     */
    public void getSingleCircleAllTalks(String circleId,int page,int rows,ResultCallback callback ){
        Map<String,String> map=new HashMap<>();
        if(!TextUtils.isEmpty(circleId)){
            map.put("circleId",circleId);
        }
        map.put("page",page+"");
        map.put("rows",rows+"");
        doPost(IZtbUrl.SINGLE_CIRCLETALK_URL,map,callback);
    }

    /**
     *  圈子说说点赞
     * @param circleTalkId
     * @param callback
     */
    public void addCircleLike(String circleTalkId ,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        if(!TextUtils.isEmpty(circleTalkId)){
            map.put("circleTalkId",circleTalkId);
        }
        doPost(IZtbUrl.CIRCLE_LIKE_URL,map,callback);
    }
    /**
     *  取消圈子说说点赞
     * @param circleTalkId
     * @param callback
     */
    public void removeCircleLike(String circleTalkId ,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        if(!TextUtils.isEmpty(circleTalkId)){
            map.put("circleTalkId",circleTalkId);
        }
        doPost(IZtbUrl.CIRCLE_UNLIKE_URL,map,callback);
    }

    /**
     * 评论圈子说说
     * @param circleTalkId
     * @param fatherCommentId
     * @param callback
     */
    public void commentCircleTalk(String circleTalkId ,String fatherCommentId ,String commentContent,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        if(!TextUtils.isEmpty(circleTalkId)){
            map.put("circleId",circleTalkId);
        }
        if (!TextUtils.isEmpty(commentContent)){
            map.put("commentContent",commentContent);
        }
        if(!TextUtils.isEmpty(UserInfoDao.getUser().getId())){
            map.put("commentUserId", UserInfoDao.getUser().getId());
        }
        if(!TextUtils.isEmpty(fatherCommentId)){
            map.put("fatherCommentId",fatherCommentId);
        }
        doPost(IZtbUrl.CIRCLE_COMMENT_URL,map,callback);
    }

    /**
     * 频道说说列表
     * @param labelId 频道说说标签ID
     * @param page
     * @param rows
     * @param callback
     */
    public void getChannelTalks(String labelId ,int page,int rows,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        if(!TextUtils.isEmpty(labelId)){
            map.put("labelId",labelId);
        }
        map.put("page",page+"");
        map.put("rows",rows+"");
        doPost(IZtbUrl.CHANNELTALK_URL,map,callback);
    }
    /**
     *  频道说说点赞
     * @param channelTalkId
     * @param callback
     */
    public void addChannelLike(String channelTalkId  ,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        if(!TextUtils.isEmpty(channelTalkId)){
            map.put("channelTalkId",channelTalkId);
        }
        doPost(IZtbUrl.CHANNEL_LIKE_URL,map,callback);
    }
    /**
     *  取消频道说说点赞
     * @param channelTalkId
     * @param callback
     */
    public void removeChannelLike(String channelTalkId ,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        if(!TextUtils.isEmpty(channelTalkId)){
            map.put("channelTalkId",channelTalkId);
        }
        doPost(IZtbUrl.CHANNEL_UNLIKE_URL,map,callback);
    }
    /**
     * 评论频道说说
     * @param channelTalkId
     * @param fatherCommentId
     * @param callback
     */
    public void commentChannelTalk(String channelTalkId  ,String fatherCommentId ,String commentContent,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        if(!TextUtils.isEmpty(channelTalkId )){
            map.put("channelTalkId",channelTalkId );
        }
        if(!TextUtils.isEmpty(fatherCommentId)){
            map.put("fatherCommentId",fatherCommentId);
        }
        if(!TextUtils.isEmpty(commentContent)){
            map.put("commentContent",commentContent);
        }
        if(!TextUtils.isEmpty(UserInfoDao.getUser().getId())){
            map.put("commentUserId",UserInfoDao.getUser().getId());
        }
        doPost(IZtbUrl.CHANNEL_COMMENT_URL,map,callback);
    }

    /**
     * 频道说说发布
     * @param channelTalkEntity
     * @param callback
     */
    public void addChannelTalk(ChannelTalkEntity channelTalkEntity,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        if(channelTalkEntity!=null){
            if(!TextUtils.isEmpty(channelTalkEntity.getLabelId())){
                map.put("labelId",channelTalkEntity.getLabelId());
            }
            if(!TextUtils.isEmpty(channelTalkEntity.getChannelContent())){
                map.put("channelContent",channelTalkEntity.getChannelContent());
            }
            if(!TextUtils.isEmpty(channelTalkEntity.getChannelImage())){
                map.put("channelImage",channelTalkEntity.getChannelImage());
            }
        }
        doPost(IZtbUrl.CHANNEL_RELEASE_URL,map,callback);
    }

    /**
     * 圈子说说评论列表
     * @param circleTalkId
     * @param page
     * @param rows
     * @param callback
     */
    public void getCircleCommentsList(String circleTalkId,int page,int rows,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        if(!TextUtils.isEmpty(circleTalkId)){
            map.put("circleTalkId",circleTalkId);
        }
        map.put("page",page+"");
        map.put("rows",rows+"");
        doPost(IZtbUrl.CIRCLE_COMMENTS_URL,map,callback);
    }

    /**
     * 频道说说评论列表
     * @param channelTalkId
     * @param page
     * @param rows
     * @param callback
     */
    public void getChannelCommentsList(String channelTalkId,int page,int rows,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        if(!TextUtils.isEmpty(channelTalkId)){
            map.put("channelTalkId",channelTalkId);
        }
        map.put("page",page+"");
        map.put("rows",rows+"");
        doPost(IZtbUrl.CHANNEL_COMMENTS_URL,map,callback);
    }

    /**
     * 上传多张图片
     * @param fileMaps
     * @param callback
     */
    public void upLoadImg(Map<String, FileBody> fileMaps, ResultCallback callback){
        submitFujian(IZtbUrl.UPLOAD_URL, fileMaps, callback);
    }

    /**
     * 我的发布
     * @param page
     * @param rows
     * @param callback
     */
    public void myTalksOut(int page,int rows,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("page",page+"");
        map.put("rows",rows+"");
        doPost(IZtbUrl.MYTALKS_OUT_URL, map, callback);
    }

    /**
     * 邀请好友加入，同意
     * @param circleId
     * @param callback
     */
    public void sendAgreeCircleInviteMsg(String circleId,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("circleId",circleId);
        doPost(IZtbUrl.INVITE_AGREE_URL,map,callback);
    }

    /**
     * 邀请加入，拒绝
     * @param circleId
     * @param callback
     */
    public void sendRefuseCircleResp(String circleId,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("circleId",circleId);
        doPost(IZtbUrl.INVITE_REFUSE_URL,map,callback);
    }

    /**
     * 请求加入，同意
     * @param circleId
     * @param personId
     * @param callback
     */
    public void sendAgreeCircleResp(String circleId,String personId,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("circleId",circleId);
        map.put("personId",personId);
        doPost(IZtbUrl.QING_AGREE_URL,map,callback);
    }

    /**
     * 请求加入，拒绝；
     * @param circleId
     * @param personId
     * @param callback
     */
    public void qingRefuseCircleResp(String circleId,String personId,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("circleId",circleId);
        map.put("personId",personId);
        doPost(IZtbUrl.QING_REFUSE_URL, map, callback);
    }

    /**
     * 申请加入圈子
     * @param circleId
     * @param callback
     */
    public void sendRequestJoinCircle(String circleId,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("circleId",circleId);
        doPost(IZtbUrl.JOINCIRCLE_URL,map,callback);
    }

    /**
     * 删除说说接口
     * @param talkId
     * @param callback
     */
    public void deleteTalk(String talkId,ResultCallback callback){
        Map<String,String> map=new HashMap<>();
        map.put("talkId",talkId);
        doPost(IZtbUrl.DELETETALK, map, callback);
    }
}
