package com.boyuanitsm.zhetengba.activity.mine;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.ArraySet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.boyuanitsm.zhetengba.MyApplication;
import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.activity.MainAct;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserBean;
import com.boyuanitsm.zhetengba.chat.DemoHelper;
import com.boyuanitsm.zhetengba.chat.db.DemoDBManager;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MD5Utils;
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by bitch-1 on 2016/4/29.
 */
public class LoginAct extends BaseActivity {
    private static final String TAG = "LoginActivity";
    private EditText usernameEditText;
    private EditText passwordEditText;

    private boolean progressShow;
    private boolean autoLogin = false;

    private String currentUsername;
    private String currentPassword;

    private ProgressDialog pd;
    private String zheTeBaId;

    @Override
    public void setLayout() {
        // 如果登录成功过，直接进入主页面
        if (DemoHelper.getInstance().isLoggedIn()) {
            autoLogin = true;
            startActivity(new Intent(LoginAct.this, MainAct.class));

            return;
        }
        setContentView(R.layout.login);
        usernameEditText = (EditText) findViewById(R.id.etPhone);
        passwordEditText = (EditText) findViewById(R.id.etPwd);
        // 如果用户名改变，清空密码
        usernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordEditText.setText(null);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Log.d(TAG, "EMClient.getInstance().onCancel");
                progressShow = false;
            }
        });
        pd.setMessage(getString(R.string.Is_landing));
    }

    @Override
    public void init(Bundle savedInstanceState) {


    }


    @OnClick({R.id.tvLogin, R.id.tv_regist, R.id.tv_forget_pw})

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLogin://登录
                if (isValidate()) {
                    toLogin(currentUsername, currentPassword);
                }
//                login();
                break;
            case R.id.tv_regist:
                openActivity(RegistAct.class);
                break;
            case R.id.tv_forget_pw:
                openActivity(FrogetpwdAct.class);
                break;
        }
    }

    /**
     * @return
     */
    private boolean isValidate() {
        currentUsername = usernameEditText.getText().toString().trim();
        currentPassword = passwordEditText.getText().toString().trim();
        if (TextUtils.isEmpty(currentUsername)) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入手机号");
            usernameEditText.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入密码");
            passwordEditText.requestFocus();
            return false;
        }

        return true;
    }


    /**
     * 登录
     *
     * @param
     */
    public void login(final UserBean userBean) {
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
//        currentUsername = usernameEditText.getText().toString().trim();
//        currentPassword = passwordEditText.getText().toString().trim();
        currentUsername=userBean.getUser().gethUsername();
        currentPassword=userBean.getUser().gethPassword();
        if (TextUtils.isEmpty(currentUsername)) {
            Toast.makeText(this, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(currentPassword)) {
            Toast.makeText(this, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }

        progressShow = true;


        // After logout，the DemoDB may still be accessed due to async callback, so the DemoDB will be re-opened again.
        // close it before login to make sure DemoDB not overlap
        DemoDBManager.getInstance().closeDB();

        // reset current user name before login
        DemoHelper.getInstance().setCurrentUserName(currentUsername);

        final long start = System.currentTimeMillis();
        // 调用sdk登陆方法登陆聊天服务器
        Log.d(TAG, "EMClient.getInstance().login");
        EMClient.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

            @Override
            public void onSuccess() {
                Log.d(TAG, "login: onSuccess");
                if (!LoginAct.this.isFinishing() && pd.isShowing()) {
                    pd.dismiss();
                }
                // ** 第一次登录或者之前logout后再登录，加载所有本地群和回话
                // ** manually load all local groups and
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                // 更新当前用户的nickname 此方法的作用是在ios离线推送时能够显示用户nick
                boolean updatenick = EMClient.getInstance().updateCurrentUserNick(
                        MyApplication.currentUserNick.trim());
                if (!updatenick) {
                    Log.e("LoginActivity", "update current user nick fail");
                }
                //异步获取当前用户的昵称和头像(从自己服务器获取，demo使用的一个第三方服务)
//                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();
                DemoHelper.getInstance().getUserProfileManager().setNickName(userBean.getUser().getUsername());
                DemoHelper.getInstance().getUserProfileManager().setUserAvatar("http://172.16.6.253:8089/zhetengba/userIcon/90017a421ee84e0db5c6d53e55c03c50.png");
                UserInfoDao.saveUser(userBean.getUser());
                // 进入主页面
                Intent intent = new Intent(LoginAct.this,
                        MainAct.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d(TAG, "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                Log.d(TAG, "login: onError: " + code);
                if (!progressShow) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message,
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (autoLogin) {
            return;
        }
    }

    private void toLogin(String username, String password) {
        pd.show();
        RequestManager.getUserManager().toLogin(username, password, new ResultCallback<ResultBean<UserBean>>() {
            @Override
            public void onError(int status, String errorMsg) {
                pd.dismiss();
                MyToastUtils.showShortToast(getApplicationContext(), errorMsg);
            }

            @Override
            public void onResponse(ResultBean<UserBean> response) {
                UserBean userBean = response.getData();
                login(userBean);
                MyLogUtils.info(userBean.getUser().getId()+"id是");
                JPushInterface.setAlias(LoginAct.this, userBean.getUser().getId(), new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {

                    }
                });
            }
        });
    }

}
