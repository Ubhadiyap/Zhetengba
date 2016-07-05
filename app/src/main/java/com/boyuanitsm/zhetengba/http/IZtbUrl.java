package com.boyuanitsm.zhetengba.http;

/**
 * 基础URl
 * Created by wangbin on 16/5/30.
 */
public interface IZtbUrl {


    //基础地址
    //外网
//    public static final String BASE_URL = "http://139.196.154.208:8010/zhetengba/";
    //开发
    public static final String BASE_URL = "http://172.16.6.253:8089/zhetengba/";
//    public static final String BASE_URL = "http://172.16.5.241:8080/zhetengba/";

    //测试
    // public static final String BASE_URL = "http://172.16.6.253:8090/zhetengba/";
    /**
     * =========完善信息接口==========
     **/
    public String PERFECT_URL = BASE_URL + "manager/login/modifyPerfectUserInfo.do";
    /**
     * =========登出地址==========
     **/
    String LOGOUT_URL = BASE_URL + "manager/unLogin/logout.do";

    /**
     * =========注册地址==========
     **/
    String REGISTER_URL = BASE_URL + "manager/unLogin/register.do";

    /**
     * =========发送验证码地址==========
     **/
    String SENDSMSCAPTCHA_URL = BASE_URL + "manager/unLogin/sendSmsCaptcha.do";

    /**
     * =========修改密码地址==========
     **/
    String MODIFYUSERPASSWORD_URL = BASE_URL + "manager/login/modifyUserPassword.do";

    /**
     * =========忘记密码地址==========
     **/
    String FORGETPASSWORD_URL = BASE_URL + "manager/unLogin/forgetPassword.do";
    /**
     * =========登录地址==========
     **/
    String LOGIN_URL = BASE_URL + "manager/unLogin/login.do";

    /**
     * =========修改用户头像==========
     **/
    String MODIFYUSERICON_URL = BASE_URL + "manager/login/modifyUserIcon.do";


    /**
     * =========修改用户信息==========
     **/
    String MODIFYUSERINFO_URL = BASE_URL + "manager/login/modifyUserInfo.do";


    /**
     * =========收藏列表接口==========
     **/
    String COLLECTIONLIST_URL = BASE_URL + "schedule/login/findCollectionListByUserId.do";

    /**
     * =========反馈接==========
     **/
    String ADDFEEDBACK_URL = BASE_URL + "schedule/login/addFeedBack.do";

    /**
     * =========获取事件信息（时间轴接口）==========
     **/
    String HISTORYMESSAGE_URL = BASE_URL + "schedule/login/findHistoryMessageListByMonth.do";

    /**
     * =========我的兴趣标签列表（非全部）==========
     **/
    String MYLABELLIST_URL = BASE_URL + "schedule/login/findMyLabelListByUserId.do";

    /**
     * =========取消收藏接口==========
     **/
    String REMOVECOLLECTION_URL = BASE_URL + "schedule/login/removeCollection.do";


    /*建立圈子*/
    String CREATE_CIRCLE_URL = BASE_URL + "talk/login/addCircle.do";
    /*我的圈子列表*/
    String CIRCLE_LIST_URL = BASE_URL + "talk/login/findCircleList.do";
    /*我的圈子详情*/
    String CIRCLE_DETAIL_URL = BASE_URL + "talk/login/findCircleInfo.do";
    /*我的圈子人员*/
    String CIRCLE_MEMBER_URL = BASE_URL + "talk/login/findCircleMember.do";
    /*搜索圈子*/
    String CIRCLE_SEARCH_URL = BASE_URL + "talk/login/findCircle.do";
    /*圈主删除成员*/
    String REMOVE_MEMBER_URL = BASE_URL + "talk/login/removeCircleMember.do";
    /*圈主发布公告*/
    String ADD_NOTICE_URL = BASE_URL + "talk/login/addCircleNotice.do";
    /*圈子成员退出圈*/
    String REMOVE_CIRCLE_URL = BASE_URL + "talk/login/removeCircle.do";
    /*圈子说说发布*/
    String ADD_CIRCLETALK_URL = BASE_URL + "talk/login/addCircleTalk.do";
    /*所有圈子说说列表*/
    String ALL_CIRCLETALK_URL = BASE_URL + "talk/login/findCircleTalkListByUser.do";
    /*单个圈子说说列表*/
    String SINGLE_CIRCLETALK_URL = BASE_URL + "talk/login/findCircleTalkListByCircleId.do";
    /*圈子说说点赞*/
    String CIRCLE_LIKE_URL = BASE_URL + "talk/login/addCircleTalkLike.do";
    /*圈子说说取消点赞*/
    String CIRCLE_UNLIKE_URL = BASE_URL + "talk/login/removeCircleTalkLike.do";
    /*圈子说说评论*/
    String CIRCLE_COMMENT_URL = BASE_URL + "talk/login/addCircleTalkComments.do";
    /*频道说说列表*/
    String CHANNELTALK_URL = BASE_URL + "talk/login/findChannelTalkList.do";
    /*频道说说点赞*/
    String CHANNEL_LIKE_URL = BASE_URL + "talk/login/addChannelTalkLike.do";
    /*取消频道说说点赞*/
    String CHANNEL_UNLIKE_URL = BASE_URL + "talk/login/removeChannelTalkLike.do";
    /*频道说说评论*/
    String CHANNEL_COMMENT_URL = BASE_URL + "talk/login/addChannelTalkComments.do";
    /*频道说说发布*/
    String CHANNEL_RELEASE_URL = BASE_URL + "talk/login/addChannelTalk.do";
    /*圈子说说评论列表*/
    String CIRCLE_COMMENTS_URL = BASE_URL + "talk/login/findCircleTalkCommentsList.do";
    /*频道说说评论列表*/
    String CHANNEL_COMMENTS_URL = BASE_URL + "talk/login/findChannelTalkCommentsList.do";
    /*我的发布*/
    String MYTALKS_OUT_URL = BASE_URL + "talk/login/findUserCircleTalk.do";
    /*上传图片*/
    String UPLOAD_URL = BASE_URL + "talk/login/uploadImg.do";
    /*邀请好友建立圈子*/
    String INVITE_FRIEND_TOCIRCLE_URL = BASE_URL + "message/login/sendCircleInviteMsg.do";
    //邀请好友同意接口
    String INVITE_AGREE_URL=BASE_URL+"message/login/sendAgreeCircleInviteMsg.do";
    //邀请好友拒绝接口
    String INVITE_REFUSE_URL=BASE_URL+"message/login/sendRefuseCircleInviteMsg.do";
    //同意加入接口
    String QING_AGREE_URL=BASE_URL+"message/login/sendAgreeCircleResp.do";
    //拒绝加入接口
    String QING_REFUSE_URL=BASE_URL+"message/login/sendRefuseCircleResp.do";
    //申请加入圈子接口
    String JOINCIRCLE_URL=BASE_URL+"talk/login/sendRequestJoinCircle.do";
    /**
     * =======档期 =======
     */

    //发布活动
    String ADD_ACTIVITY = BASE_URL + "schedule/login/addActivity.do";
    //banner轮播图
    String BANNER_URL = BASE_URL + "schedule/login/findActivityBanner.do";
    //活动列表
    String ACTIVITY_LIST = BASE_URL + "schedule/login/findActivityList.do";
    //活动详情
    String ACTIVITY_DETIALS = BASE_URL + "schedule/login/findActivityDetails.do";
    //关注活动
    String ACTIVITY_COLLECTION_URL = BASE_URL + "schedule/login/addActivityCollection.do";
    //活动标签
    String ACTIVITY_LABEL_URL = BASE_URL + "schedule/login/findActivityLabel.do";
    //相应（参加）活动
    String RESPOND_ACTIVITY_URL = BASE_URL + "schedule/login/respondActivity.do";
    //好友/全部
    String FRIEND_ALL_URL = BASE_URL + "schedule/login/findActivityAllOrFriends.do";
    //获取兴趣标签
    String INTREST_LABEL_URL = BASE_URL + "schedule/login/findLabelList.do";
    //添加兴趣标签
    String ADD_INTREST_LABEL = BASE_URL + "schedule/login/addInterestLabel.do";

    //我的兴趣标签列表(MORE)接口
    String MY_INTEREST_URL = BASE_URL + "schedule/login/findMyLabelListMoreByUserId.do";

    //档期banner轮播图
    String BANNER_CAN_URL = BASE_URL + "schedule/login/findScheduleBanner.do";


    //档期列表
    String SCHEDULE_LIST_URL = BASE_URL + "schedule/login/findScheduleList.do";

    //点击约她匹配列表
    String FINDMATCHING_URL=BASE_URL +"schedule/login/findMatchingActivities.do";


    //关注档期接口
    String SCHEDULE_COLLECTION_URL = BASE_URL + "schedule/login/addScheduleCollection.do";


    //约Ta接口
    String ABOUT_URL = BASE_URL + "schedule/login/addAbout.do";


    //档期显示全部/好友接口
    String SCHEDULE_FRIEND_URL = BASE_URL + "schedule/login/findScheduleAllOrFriends.do";

    //活动指定谁看，通知谁
    String ACTIVITY_NOTICE_URL = BASE_URL + "schedule/login/sendActivityNotice.do";


    //档期指定谁看，通知谁
    String SHEDULE_NOTICE_URL = BASE_URL + "schedule/login/sendScheduleNotice.do";


    //活动不让谁看
    String ACTIVITY_INVISIBLE_URL = BASE_URL + "schedule/login/addActivityInvisible.do";


    //档期不让谁看
    String SHEDULE_INVISIBLE_URL = BASE_URL + "schedule/login/addScheduleInvisible.do";


    //活动仅好友可见
    String ACTIVITY_FRIEND_URL = BASE_URL + "schedule/login/modifyActivityFriendSee.do";


    //档期仅好友可见
    String SHEDULE_FRIEND_URL = BASE_URL + "schedule/login/modifyScheduleFriendSee.do";


    //自己发布的活动 接口
    String MY_ACTIVITY_URL = BASE_URL + "schedule/login/findMyActivity.do";


    //取消活动
    String REMOVE_ACTIVITY_URL = BASE_URL + "schedule/login/removeActivity.do";
    //取消档期
    String REMOVE_SHEDULE_URL = BASE_URL + "schedule/login/removeSchedule.do";

    //发布档期接口
    String ADD_SHEDULE_URL = BASE_URL + "schedule/login/addSchedule.do";

    //发布活动推送
    String ACTIVITY_MESSAGE_URL = BASE_URL + "schedule/login/pushActivityMessage.do";
    //发布档期推送
    String SHEDULE_MESSAGE_URL = BASE_URL + "schedule/login/pushScheduleMessage.do";
    //取消活动参加
    String CANCEL_ACTIVITY_URL = BASE_URL + "schedule/login/quitActivity.do";
    //档期同意接口
    String AGREE_ACTIVITY_URL=BASE_URL+"message/login/sendAgreeActivityResp.do";
    //档期拒绝
    String REFUSE_ACTIVITY_URL=BASE_URL+"message/login/sendRefuseActivityResp.do";
//    //个人主页接口
//
//    public static  final  String PERSONAL_HOME_PAGE_URL=BASE_URL+"schedule/login/findPersonalByUserId.do";
//    //添加好友接口
//    public static final String ADD_FRIEND_URL="message/login/addFriendFromContact.do";


    String PERSONAL_HOME_PAGE_URL = BASE_URL + "schedule/login/findPersonalByUserId.do";
    //添加换新群组接口
    String ADD_HGROUP_URL=BASE_URL+"message/login/addHGroup.do";
    //取消参加移除群组
    String DELET_GROUP_URL=BASE_URL+"message/login/removeHGroup.do";


    /**
     * =========消息==========
     **/
    //通过手机号搜索用户
    String FINDUSERBYPHONE_URL = BASE_URL + "message/login/findUserByphoneNum.do";
    //同意添加好友
    String ADD_FRIEND_URL = BASE_URL + "message/login/addFriend.do";
    //删除好友
    String DELETE_FRIEND_URL = BASE_URL + "message/login/removeFriend.do";
    //好友列表
    String FRIEND_LIST_URL = BASE_URL + "message/login/findFriendsList.do";
    //添加好友
    String ADD_FRIEND_CONTACT = BASE_URL + "message/login/addFriendFromContact.do";
    //修改昵称
    String UPDATE_NICKNAME = BASE_URL + "message/login/modifyNickName.do";
    /*创建群聊*/
    String ADD_GROUP_URL=BASE_URL+"message/login/addGroupChat.do";
    /*获取群成员列表*/
    String GET_GROUP_MEMBER_URL=BASE_URL+"message/login/findGroupMember.do";
    /*移除群组成员*/
    String REMOVE_GROUP_PERSON_URL=BASE_URL+"message/login/removeUsersFromGroup.do";
    /*退出群聊*/
    String EXIT_GROUP_URL=BASE_URL+"message/login/removeFromGroup.do";
    /*删除好友*/
    String REMOVE_FRIEND_URL=BASE_URL+"message/login/removeFriend.do";
    /*增加群成员*/
    String ADD_GROUP_MEMBER_URL=BASE_URL+"message/login/addGroupMember.do";
    /*查找群详情*/
    String GROUP_INFO=BASE_URL+"message/login/findGroupInfo.do";
    /*查找用户*/
    String FIND_USER_URL=BASE_URL+"message/login/findUserIcon.do";
//    String ADD_GROUP_URL = BASE_URL + "message/login/addGroupChat.do";

    /*设置里面添加好友需要验证/不需要验证*/
    String ISCHECKED_URL=BASE_URL+"message/login/isChecked.do";
    /*获取用户信息*/
    String FINDUSERICON_URL=BASE_URL+"message/login/findUserIcon.do";

    //版本更新
    String FIND_NEW_APP=BASE_URL+"manager/unLogin/checkUpdrage.do";

}
