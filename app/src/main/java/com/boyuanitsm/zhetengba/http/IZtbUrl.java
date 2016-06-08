package com.boyuanitsm.zhetengba.http;

/**
 * 基础URl
 * Created by wangbin on 16/5/30.
 */
public interface IZtbUrl {

    //基础地址


    public static final String BASE_URL = "http://172.16.6.253:8089/zhetengba/";
    //测试
//    public static final String BASE_URL = "http://172.16.5.242:8080/zhetengba/";

   // public  String BASE_URL="http://172.16.6.253:8089/zhetengba/";

    /**=========登出地址==========**/
    public  String LOGOUT_URL=BASE_URL+"manager/unLogin/logout.do";

    /**=========注册地址==========**/
    public  String REGISTER_URL=BASE_URL+"manager/unLogin/register.do";

    /**=========发送验证码地址==========**/
    public  String SENDSMSCAPTCHA_URL=BASE_URL+"manager/unLogin/sendSmsCaptcha.do";

    /**=========修改密码地址==========**/
    public  String MODIFYUSERPASSWORD_URL=BASE_URL+"manager/login/modifyUserPassword.do";

    /**=========忘记密码地址==========**/
    public  String FORGETPASSWORD_URL=BASE_URL+"manager/unLogin/forgetPassword.do";
    /**=========登录地址==========**/
    public  String LOGIN_URL=BASE_URL+"manager/unLogin/login.do";

    /**=========修改用户头像==========**/
    public  String MODIFYUSERICON_URL=BASE_URL+"manager/login/modifyUserIcon.do";




    /**=========修改用户信息==========**/
    public  String MODIFYUSERINFO_URL=BASE_URL+"manager/login/modifyUserInfo.do";


    /**=========收藏列表接口==========**/
    public  String COLLECTIONLIST_URL=BASE_URL+"schedule/login/findCollectionListByUserId.do";

    /**=========反馈接==========**/
    public  String ADDFEEDBACK_URL=BASE_URL+"schedule/login/addFeedBack.do";

    /**=========获取事件信息（时间轴接口）==========**/
    public  String HISTORYMESSAGE_URL=BASE_URL+"schedule/login/findHistoryMessageListByUserId.do";

//    /**=========我的兴趣标签列表（非全部）==========**/
//    public  String MYLABELLIST_URL=BASE_URL+"schedule/login/findMyLabelListByUserId.do";

    /**=========取消收藏接口==========**/
    public  String REMOVECOLLECTION_URL=BASE_URL+"schedule/login/removeCollection.do";

    /*建立圈子*/
    String CREATE_CIRCLE_URL=BASE_URL+"talk/login/addCircle.do";
    /*我的圈子列表*/
    String CIRCLE_LIST_URL=BASE_URL+"talk/login/findCircleList.do";
    /*我的圈子详情*/
    String CIRCLE_DETAIL_URL=BASE_URL+"talk/login/findCircleInfo.do";
    /*我的圈子人员*/
    String CIRCLE_MEMBER_URL=BASE_URL+"talk/login/findCircleMember.do";
    /*搜索圈子*/
    String CIRCLE_SEARCH_URL=BASE_URL+"talk/login/findCircle.do";
    /*圈主删除成员*/
    String REMOVE_MEMBER_URL=BASE_URL+"talk/login/removeCircleMember.do";
    /*圈主发布公告*/
    String ADD_NOTICE_URL=BASE_URL+"talk/login/addCircleNotice.do";
    /*圈子成员退出圈*/
    String REMOVE_CIRCLE_URL=BASE_URL+"talk/login/removeCircle.do";
    /*圈子说说发布*/
    String ADD_CIRCLETALK_URL=BASE_URL+"talk/login/addCircleTalk.do";
    /*所有圈子说说列表*/
    String ALL_CIRCLETALK_URL=BASE_URL+"talk/login/findCircleTalkListByUser.do";
    /*单个圈子说说列表*/
    String SINGLE_CIRCLETALK_URL=BASE_URL+"talk/login/findCircleTalkListByCircleId.do";
    /*圈子说说点赞*/
    String CIRCLE_LIKE_URL=BASE_URL+"talk/login/addCircleTalkLike.do";
    /*圈子说说取消点赞*/
    String CIRCLE_UNLIKE_URL=BASE_URL+"talk/login/removeCircleTalkLike.do";
    /*圈子说说评论*/
    String CIRCLE_COMMENT_URL=BASE_URL+"talk/login/addCircleTalkComments.do";
    /*频道说说列表*/
    String CHANNELTALK_URL=BASE_URL+"talk/login/findChannelTalkList.do";
    /*频道说说点赞*/
    String CHANNEL_LIKE_URL=BASE_URL+"talk/login/addChannelTalkLike.do";
    /*取消频道说说点赞*/
    String CHANNEL_UNLIKE_URL=BASE_URL+"talk/login/removeChannelTalkLike.do";
    /*频道说说评论*/
    String CHANNEL_COMMENT_URL=BASE_URL+"talk/login/addChannelTalkComments.do";
    /*频道说说发布*/
    String CHANNEL_RELEASE_URL=BASE_URL+"talk/login/addChannelTalk.do";
    /*圈子说说评论列表*/
    String CIRCLE_COMMENTS_URL=BASE_URL+"talk/login/findCircleTalkCommentsList.do";
    /*频道说说评论列表*/
    String CHANNEL_COMMENTS_URL=BASE_URL+"talk/login/findChannelTalkCommentsList.do";






    /**=======档期 =======*/

    //发布活动
    public static final String ADD_ACTIVITY=BASE_URL+"schedule/login/addActivity.do";
    //banner轮播图
    public static final String BANNER_URL=BASE_URL+"schedule/login/findActivityBanner.do";
    //活动列表
    public static final String ACTIVITY_LIST=BASE_URL+"schedule/login/findActivityList.do";
    //活动详情
    public static final String ACTIVITY_DETIALS=BANNER_URL+"schedule/login/findActivityDetails.do";
    //关注活动
    public static final String ACTIVITY_COLLECTION_URL=BASE_URL+"schedule/login/addActivityCollection.do";
    //活动标签
    public static final String ACTIVITY_LABEL_URL=BASE_URL+"schedule/login/findActivityLabel.do";
    //相应（参加）活动
    public static final String RESPOND_ACTIVITY_URL=BASE_URL+"schedule/login/respondActivity.do";
    //好友/全部
    public static final String FRIEND_ALL_URL=BASE_URL+"schedule/login/findActivityAllOrFriends.do";
    //获取兴趣标签
    public static final String INTREST_LABEL_URL=BASE_URL+"schedule/login/findLabelList.do";
    //添加兴趣标签
    public static final String ADD_INTREST_LABEL=BASE_URL+"schedule/login/addInterestLabel.do";

   //我的兴趣标签列表(MORE)接口
   public static final String MY_INTEREST_URL=BASE_URL+"schedule/login/findMyLabelListMoreByUserId.do";

    //档期banner轮播图
    public static final String BANNER_CAN_URL=BASE_URL+"schedule/login/findScheduleBanner.do";


    //档期列表
    public static final String SCHEDULE_LIST_URL=BASE_URL+"schedule/login/findScheduleList.do";


    //关注档期接口
    public static final String SCHEDULE_COLLECTION_URL=BASE_URL+"schedule/login/addScheduleCollection.do";


    //约Ta接口
    public static final String ABOUT_URL=BASE_URL+"schedule/login/addAbout.do";


    //档期显示全部/好友接口
    public static  final String SCHEDULE_FRIEND_URL=BASE_URL+"schedule/login/findScheduleAllOrFriends.do";

    //活动指定谁看，通知谁
    public static final String ACTIVITY_NOTICE_URL=BASE_URL+"schedule/login/sendActivityNotice.do";


    //档期指定谁看，通知谁
    public static final String SHEDULE_NOTICE_URL=BASE_URL+"schedule/login/sendScheduleNotice.do";


    //活动不让谁看
    public static final String ACTIVITY_INVISIBLE_URL=BASE_URL+"schedule/login/addActivityInvisible.do";


    //档期不让谁看
    public static final String SHEDULE_INVISIBLE_URL=BASE_URL+"schedule/login/addScheduleInvisible.do";


    //活动仅好友可见
    public static final String ACTIVITY_FRIEND_URL=BASE_URL+"schedule/login/modifyActivityFriendSee.do";


    //档期仅好友可见
    public static final String SHEDULE_FRIEND_URL=BASE_URL+"schedule/login/modifyScheduleFriendSee.do";


    //自己发布的活动 接口
    public static final String MY_ACTIVITY_URL=BASE_URL+"schedule/login/findMyActivity.do";


    //取消活动
    public static final String REMOVE_ACTIVITY_URL=BASE_URL+"schedule/login/removeActivity.do";


    //发布档期接口
    public static final String ADD_SHEDULE_URL=BASE_URL+"schedule/login/addSchedule.do";

    //发布活动推送
    public static final String ACTIVITY_MESSAGE_URL=BASE_URL+"schedule/login/pushActivityMessage.do";
    //发布档期推送
    public static final String SHEDULE_MESSAGE_URL=BASE_URL+"schedule/login/pushScheduleMessage.do";

}
