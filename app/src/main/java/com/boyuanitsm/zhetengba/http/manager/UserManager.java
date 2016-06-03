package com.boyuanitsm.zhetengba.http.manager;

import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.http.IZtbUrl;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
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
     * @param
     * @param captcha
     * @param
     * @param callback
     */
    public void register(String username,String captcha,String password,ResultCallback callback){
       Map<String,String> params=new HashMap<>();
        params.put("username",username);
        params.put("captcha",captcha);
        params.put("password",password);
        doPost(IZtbUrl.REGISTER_URL, params, callback);
    }


    /**
     * 发送验证码
     * @param phoneNumber
     * @param isRegister
     * @param callback
     */
    public void sendSmsCaptcha(String phoneNumber,String isRegister,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("phoneNumber",phoneNumber);
        params.put("isRegister",isRegister);
        doPost(IZtbUrl.SENDSMSCAPTCHA_URL, params, callback);
    }

    /**
     * 忘记密码
     * @param sms
     * @param newPassword
     * @param callback
     */
    public void forgetPassword(String sms,String newPassword,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("sms",sms);
        params.put("newPassword",newPassword);
        doPost(IZtbUrl.FORGETPASSWORD_URL, params, callback);

    }

    /**
     * 修改用户资料
     * @param userInfo
     * @param callback
     */
    public void modifyUserInfo(UserInfo userInfo,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        Gson gson = new Gson();
        String json = gson.toJson(userInfo);
        try {
            JSONObject obj = new JSONObject(json);
            Iterator<String> it = obj.keys();
            while (it.hasNext()) {
                String key = it.next();
                params.put(key, String.valueOf(obj.get(key)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        doPost(IZtbUrl.MODIFYUSERINFO_URL, params, callback);

    }

    /**
     *我的frg收藏列表
     * @param page
     * @param rows
     * @param callback
     */
    public void findCollectionListByUserId(int page,int rows,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("page",page+"");
        params.put("rows",rows+"");
        doPost(IZtbUrl.COLLECTIONLIST_URL, params, callback);

    }


    /**
     * 反馈接口
     * @param content
     * @param callback
     */
    public void addFeedBack(String content,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("content",content);
        doPost(IZtbUrl.ADDFEEDBACK_URL,params,callback);
    }


//    /**
//     * 我的兴趣标签列表
//     * @param
//     * @param callback
//     */
//    public void findMyLabelListByUserId(int limitNum ,ResultCallback callback){
//        Map<String,String> params=new HashMap<>();
//        params.put("limitNum",limitNum+"");
//        doPost(IZtbUrl.MYLABELLIST_URL,params,callback);
//    }
//







}
