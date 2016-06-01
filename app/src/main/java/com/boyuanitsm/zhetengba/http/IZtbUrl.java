package com.boyuanitsm.zhetengba.http;

/**
 * 基础URl
 * Created by wangbin on 16/5/30.
 */
public interface IZtbUrl {

    //基础地址


//    public static final String BASE_URL = "http://172.16.6.253:8089/zhetengba/";
    //测试
    public static final String BASE_URL = "http://172.16.6.225:8080/zhetengba/";

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
    //相应（参加）活动
    public static final String RESPOND_ACTIVITY_URL=BASE_URL+"schedule/login/respondActivity.do";
    //好友/全部
    public static final String FRIEND_ALL_URL=BASE_URL+"schedule/login/findActivityAllOrFriends.do";
    //获取兴趣标签
    public static final String INTREST_LABEL_URL=BASE_URL+"schedule/login/findLabelList.do";
    //添加兴趣标签
    public static final String ADD_INTREST_LABEL=BASE_URL+"schedule/login/addInterestLabel.do";
}
