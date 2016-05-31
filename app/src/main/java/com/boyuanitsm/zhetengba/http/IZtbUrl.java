package com.boyuanitsm.zhetengba.http;

/**
 * 基础URl
 * Created by wangbin on 16/5/30.
 */
public interface IZtbUrl {

    //基础地址
    public static final String BASE_URL="http://172.16.6.253:8089/zhetengba/";

    /**=========登出地址==========**/
    public static final String LOGOUT_URL=BASE_URL+"manager/unLogin/logout.do";

    /**=========注册地址==========**/
    public static final String REGISTER_URL=BASE_URL+"manager/unLogin/register.do";

    /**=========发送验证码地址==========**/
    public static final String SENDSMSCAPTCHA_URL=BASE_URL+"manager/unLogin/sendSmsCaptcha.do";

    /**=========修改密码地址==========**/
    public static final String MODIFYUSERPASSWORD_URL=BASE_URL+"manager/login/modifyUserPassword.do";

    /**=========忘记密码地址==========**/
    public static final String FORGETPASSWORD_URL=BASE_URL+"manager/unLogin/forgetPassword.do";

    public static final String LOGIN_URL=BASE_URL+"manager/unLogin/login.do";

}
