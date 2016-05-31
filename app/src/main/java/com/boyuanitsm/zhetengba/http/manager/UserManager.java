package com.boyuanitsm.zhetengba.http.manager;

import com.boyuanitsm.zhetengba.http.IZtbUrl;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户相关
 * Created by wangbin on 16/5/30.
 */
public class UserManager extends RequestManager{

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @param callback
     */
    public void toLogin(String username,String password,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("username",username);
        params.put("password",password);
        doPost(IZtbUrl.LOGIN_URL,params,callback);
    }


    /**
     * 登出
     * @param callback
     */
    public void Loginout(ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        doPost(IZtbUrl.LOGOUT_URL,params,callback);
    }


    /**
     * 注册
     * @param username
     * @param captcha
     * @param password
     * @param callback
     */
    public void register(String username,String captcha,String password,ResultCallback callback){
       Map<String,String> params=new HashMap<>();
        params.put("username",username);
        params.put("captcha",captcha);
        params.put("password",password);
        doPost(IZtbUrl.REGISTER_URL, params, callback);
    }


    public void sendSmsCaptcha(String phoneNumber,String isRegister,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("phoneNumber",phoneNumber);
        params.put("isRegister",isRegister);
        doPost(IZtbUrl.SENDSMSCAPTCHA_URL, params, callback);
    }






}
