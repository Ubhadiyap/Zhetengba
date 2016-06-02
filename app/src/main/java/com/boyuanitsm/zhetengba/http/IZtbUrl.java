package com.boyuanitsm.zhetengba.http;

/**
 * 基础URl
 * Created by wangbin on 16/5/30.
 */
public interface IZtbUrl {

    //基础地址
    public  String BASE_URL="http://172.16.6.253:8089/zhetengba/";

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




}
