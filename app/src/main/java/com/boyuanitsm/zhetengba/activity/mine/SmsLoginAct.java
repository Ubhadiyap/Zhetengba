package com.boyuanitsm.zhetengba.activity.mine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.Timer;
import java.util.TimerTask;

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
    private int i = 60;
    private Timer timer;
    private MyTimerTask myTask;
    private String phone;
    private String aqm;
    @Override
    public void setLayout() {
        setContentView(R.layout.sms_act);
    }

    @Override
    public void init(Bundle savedInstanceState) {

    }
    @OnClick({R.id.tv_code,R.id.tv_frogettj,R.id.iv_aqm})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.tv_code://获取验证码
                phone=et_phone.getText().toString().trim();
                aqm=et_aqm.getText().toString().trim();
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
                if(!ZhetebaUtils.checkCellPhone(phone)){
                    MyToastUtils.showShortToast(getApplicationContext(), "请输入正确的手机号码");
                    return;
                }
                sendSms(phone, "false",aqm);
                break;

        }
    }
    /**
     * 倒计时
     *
     * @author wangbin
     *
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
     * 发送验证码接口
     * @param phoneNumber
     * @param isRegister
     */
    public void sendSms(String phoneNumber,String isRegister,String imageCaptcha){
        tv_code.setEnabled(false);
        RequestManager.getUserManager().sendSmsCaptcha(phoneNumber, isRegister,imageCaptcha, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
                tv_code.setEnabled(true);
                MyToastUtils.showShortToast(getApplicationContext(), errorMsg);
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
}
