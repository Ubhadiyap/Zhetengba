package com.boyuanitsm.zhetengba.activity.mine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
import com.boyuanitsm.zhetengba.utils.MyLogUtils;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.Uitls;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.boyuanitsm.zhetengba.view.MyAlertDialog;
import com.google.gson.Gson;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * 验证码登录
 * Created by xiaoke on 2016/11/29.
 */
public class SmsLoginAct extends BaseActivity {

    @ViewInject(R.id.tv_code)
    private TextView tv_code;
    @ViewInject(R.id.et_aqm)
    private EditText et_aqm;
    @ViewInject(R.id.et_phone)
    private EditText et_phone;
    @ViewInject(R.id.tvLogin)
    private TextView tvLogin;
    private int i = 60;
    private Timer timer;
    private MyTimerTask myTask;
    private String phone;
    private String aqm;
    private String currentUsername, currentPassword;
    private boolean progressShow;
    private ProgressDialog pd;

    @Override
    public void setLayout() {
        setContentView(R.layout.sms_act);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("验证码登录");
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
//                Log.d(TAG, "EMClient.getInstance().onCancel");
                progressShow = false;
            }
        });
        pd.setMessage(getString(R.string.Is_landing));
    }

    @OnClick({R.id.tv_code, R.id.tv_frogettj, R.id.tvLogin})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_code://获取验证码
                phone = et_phone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入手机号");
                    return;
                }
                if (phone.length() != 11) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入11的手机号");
                    et_phone.requestFocus();
                    et_phone.setSelection(et_phone.length());
                    return;
                }
                if (!ZhetebaUtils.checkCellPhone(phone)) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入正确的手机号码");
                    return;
                }
                getSms(phone, "false");
                break;
            case R.id.tvLogin:
                phone = et_phone.getText().toString().trim();
                aqm = et_aqm.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入手机号");
                    return;
                }
                if (phone.length() != 11) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入11的手机号");
                    et_phone.requestFocus();
                    et_phone.setSelection(et_phone.length());
                    return;
                }
                if (!ZhetebaUtils.checkCellPhone(phone)) {
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入正确的手机号码");
                    return;
                }
                if (TextUtils.isEmpty(aqm)) {
                    MyToastUtils.showShortToast(getApplicationContext(), "短信验证码不能为空");
                    return;
                }
                sendSms(phone, aqm);
                break;


        }
    }

    /**
     * 获取验证码
     *
     * @param phone
     * @param isRegister
     */
    private void getSms(final String phone, String isRegister) {
        tv_code.setEnabled(false);
        RequestManager.getUserManager().getSms2(phone, isRegister, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
                tv_code.setEnabled(true);
                 if (500 == status) {
                    MyAlertDialog dialog = new MyAlertDialog(SmsLoginAct.this);
                    dialog.builder().setTitle("提示").setMsg("您尚未注册，请先注册再登录APP，是否注册?").setPositiveButton("立即注册", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(SmsLoginAct.this, RegistAct.class);
                            intent.putExtra("phoneNum", phone);
                            startActivity(intent);
                        }
                    }).setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
                }else {
                     MyToastUtils.showShortToast(getApplicationContext(), errorMsg);
                 }
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                i = 60;
                timer = new Timer();
                myTask = new MyTimerTask();
                timer.schedule(myTask, 0, 1000);
                MyToastUtils.showShortToast(getApplicationContext(), "验证码发送成功");

            }
        });
    }

    /**
     * 倒计时
     *
     * @author wangbin
     */
    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            handler.sendEmptyMessage(i--);
        }

    }

    /**
     * 这个用来处理倒计时的handler;
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0 || msg.what < 0) {
                tv_code.setEnabled(true);
                tv_code.setText("重新发送");
                timer.cancel();
                myTask.cancel();
            } else {
                tv_code.setText(msg.what + "秒");

            }
        }

    };

    /**
     * 验证码登录
     *
     * @param phoneNumber
     */
    public void sendSms(final String phoneNumber, String imageCaptcha) {
        tvLogin.setEnabled(false);
        RequestManager.getUserManager().loginSms(phoneNumber, imageCaptcha, new ResultCallback<ResultBean<UserBean>>() {
            @Override
            public void onError(int status, String errorMsg) {
                tv_code.setEnabled(true);
                if (601 == status) {
                    try {
                        Gson mGson = new Gson();
                        JSONObject json = new JSONObject(errorMsg);
                        JSONObject data = json.getJSONObject("data");
                        UserBean userBean = mGson.fromJson(data.toString(), UserBean.class);
                        login(userBean, 0);
                        JPushInterface.setAlias(SmsLoginAct.this, userBean.getUser().getId(), new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {

                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    MyToastUtils.showShortToast(getApplicationContext(), errorMsg);
                }

                pd.dismiss();
            }


            @Override
            public void onResponse(ResultBean<UserBean> response) {
                tvLogin.setEnabled(true);
                UserBean userBean = response.getData();
                login(userBean, 1);
                MyLogUtils.info(userBean.getUser().getId() + "id是");
                JPushInterface.setAlias(SmsLoginAct.this, userBean.getUser().getId(), new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {

                    }
                });
            }
        });


    }

    /**
     * 登录
     *
     * @param
     */
    public void login(final UserBean userBean, final int type) {
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            Toast.makeText(this, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
//        currentUsername = usernameEditText.getText().toString().trim();
//        currentPassword = passwordEditText.getText().toString().trim();
        currentUsername = userBean.getUser().gethUsername();
        currentPassword = userBean.getUser().gethPassword();
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
//        Log.d(TAG, "EMClient.getInstance().login");
        EMClient.getInstance().login(currentUsername, currentPassword, new EMCallBack() {

            @Override
            public void onSuccess() {
//                Log.d(TAG, "login: onSuccess");
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
                DemoHelper.getInstance().getUserProfileManager().setNickName(userBean.getUser().getPetName());
                DemoHelper.getInstance().getUserProfileManager().setUserAvatar(Uitls.imageFullUrl(userBean.getUser().getIcon()));
                UserInfoDao.saveUser(userBean.getUser());

                if (!SmsLoginAct.this.isFinishing() && pd.isShowing()) {
                    pd.dismiss();
                }

                if (type == 0) {
                    //进入完善信息界面
                    Intent intent = new Intent(SmsLoginAct.this, RegInfoAct.class);
                    startActivity(intent);
                    finish();
                } else if (type == 1) {
                    // 进入主页面
                    Intent intent = new Intent(SmsLoginAct.this,
                            MainAct.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onProgress(int progress, String status) {
//                Log.d(TAG, "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
//                Log.d(TAG, "login: onError: " + code);
                if (!progressShow) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + "请检查网络",
                                Toast.LENGTH_SHORT).show();//登录失败，请检查网络
                    }
                });
            }
        });
    }
}
