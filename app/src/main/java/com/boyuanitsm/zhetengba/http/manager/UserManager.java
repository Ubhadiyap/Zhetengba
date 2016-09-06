package com.boyuanitsm.zhetengba.http.manager;

import android.text.TextUtils;

import com.boyuanitsm.zhetengba.bean.UserInfo;
import com.boyuanitsm.zhetengba.http.IZtbUrl;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.google.gson.Gson;
import com.lidroid.xutils.http.client.multipart.content.FileBody;

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
        params.put("password", password);
        OkHttpManager.getInstance().doPost(IZtbUrl.LOGIN_URL, params, callback);
    }


    /**
     * 登出
     * @param callback
     */
    public void Loginout(ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        OkHttpManager.getInstance().doPost(IZtbUrl.LOGOUT_URL, params, callback);
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
        params.put("password", password);
        OkHttpManager.getInstance().doPost(IZtbUrl.REGISTER_URL, params, callback);
    }


    /**
     * 发送验证码
     * @param phoneNumber
     * @param isRegister
     * @param callback
     */
    public void sendSmsCaptcha(String phoneNumber,String isRegister,int identifyCode,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("phoneNumber",phoneNumber);
        params.put("isRegister", isRegister);
        params.put("identifyCode",identifyCode+"");
        OkHttpManager.getInstance().doPost(IZtbUrl.SENDSMSCAPTCHA_URL, params, callback);
    }

    public void findImgCaptcha(ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        OkHttpManager.getInstance().doPost(IZtbUrl.FINDIMGCAPTCHA_URL, params, callback);
    }

    /**
     * 忘记密码
     * @param captcha
     * @param newPassword
     * @param callback
     */
    public void forgetPassword(String phoneNumber,String captcha,String newPassword,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("phoneNumber",phoneNumber);
        params.put("captcha",captcha);
        params.put("newPassword", newPassword);
        OkHttpManager.getInstance().doPost(IZtbUrl.FORGETPASSWORD_URL, params, callback);

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
            if ((obj.toString()).indexOf("createTime") != -1) {
                obj.remove("createTime");
            }
            if ((obj.toString()).indexOf("username") != -1) {
                obj.remove("username");
            }
            if ((obj.toString()).indexOf("modifyTime") != -1) {
                obj.remove("modifyTime");
            }
            if ((obj.toString()).indexOf("password") != -1) {
                obj.remove("password");
            }
            if ((obj.toString()).indexOf("icon") != -1) {
                obj.remove("icon");
            }
            Iterator<String> it = obj.keys();
            while (it.hasNext()) {
                String key = it.next();
                params.put(key, String.valueOf(obj.get(key)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkHttpManager.getInstance().doPost(IZtbUrl.MODIFYUSERINFO_URL, params, callback);

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
        OkHttpManager.getInstance().doPost(IZtbUrl.COLLECTIONLIST_URL, params, callback);

    }


    /**
     * 反馈接口
     * @param content
     * @param callback
     */
    public void addFeedBack(String content,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("content",content);
        OkHttpManager.getInstance().doPost(IZtbUrl.ADDFEEDBACK_URL, params, callback);
    }

    /**
     * 当前版本
     * 平台
     * @param currentVersion
     * @param platform
     */
    public void findNewApp(int currentVersion,String platform ,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("currentVersion",currentVersion+"");
        params.put("platform",platform);
        OkHttpManager.getInstance().doPost(IZtbUrl.FIND_NEW_APP, params, callback);

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


    /**
     * 上传头像
     * @param fileMaps
     * @param callback
     */
    public void subHeadImg(Map<String,FileBody> fileMaps,ResultCallback callback){
        submitFujian(IZtbUrl.MODIFYUSERICON_URL, fileMaps, callback);
    }


    /**
     * 完善个人信息
     * @param userInfo
     * @param labelIds
     * @param callback
     */
    public void perfect(UserInfo userInfo,String labelIds,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("labelIds",labelIds);
        params.put("petName",userInfo.getPetName());
        if(!TextUtils.isEmpty(userInfo.getSex()))
        params.put("sex",userInfo.getSex());

//        Gson gson = new Gson();
//        String json = gson.toJson(userInfo);
//        try {
//            JSONObject obj = new JSONObject(json);
//            if ((obj.toString()).indexOf("createTime") != -1) {
//                obj.remove("createTime");
//            }
//            if ((obj.toString()).indexOf("username") != -1) {
//                obj.remove("username");
//            }
//            if ((obj.toString()).indexOf("modifyTime") != -1) {
//                obj.remove("modifyTime");
//            }
//            if ((obj.toString()).indexOf("password") != -1) {
//                obj.remove("password");
//            }
//            if ((obj.toString()).indexOf("icon") != -1) {
//                obj.remove("icon");
//            }
//            if ((obj.toString()).indexOf("id") != -1) {
//                obj.remove("id");
//            }
//            Iterator<String> it = obj.keys();
//            while (it.hasNext()) {
//                String key = it.next();
//                params.put(key, String.valueOf(obj.get(key)));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        OkHttpManager.getInstance().doPost(IZtbUrl.PERFECT_URL, params, callback);


    }

    /**
     * 提现
     * @param amount
     * @param callback
     */
    public void getMoney(String amount,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("amount",amount+"");
        OkHttpManager.getInstance().doPost(IZtbUrl.CASHBALANCE_URL, params, callback);

    }

    /**
     * 获取账单明细
     * @param page
     * @param rows
     * @param callback
     */
    public void getBilldeta(int page,int rows,ResultCallback callback){
        Map<String,String> params=new HashMap<>();
        params.put("page",page+"");
        params.put("rows",rows+"");
        OkHttpManager.getInstance().doPost(IZtbUrl.BILLDETAILS_URL, params, callback);

    }






}
