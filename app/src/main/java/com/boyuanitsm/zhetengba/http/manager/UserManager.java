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


}
