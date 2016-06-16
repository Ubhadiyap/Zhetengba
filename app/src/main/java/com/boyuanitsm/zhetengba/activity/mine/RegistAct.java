package com.boyuanitsm.zhetengba.activity.mine;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.bean.ResultBean;
import com.boyuanitsm.zhetengba.bean.UserBean;
import com.boyuanitsm.zhetengba.db.UserInfoDao;
import com.boyuanitsm.zhetengba.http.callback.ResultCallback;
import com.boyuanitsm.zhetengba.http.manager.RequestManager;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.boyuanitsm.zhetengba.utils.ZhetebaUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 注册界面
 * Created by bitch-1 on 2016/4/28.
 */
public class RegistAct extends BaseActivity {
    @ViewInject(R.id.et_phone)
    private EditText et_phone;
    @ViewInject(R.id.et_pwd)
    private EditText et_pwd;
    @ViewInject(R.id.et_cpwd)
    private EditText et_cpwd;
    @ViewInject(R.id.et_yzm)
    private EditText et_yzm;
    @ViewInject(R.id.tv_code)
    private TextView tv_code;
    @ViewInject(R.id.register_cb)
    private CheckBox register_cb;


    private String phone, yzm,pwd,cpwd;//手机号，验证码，确认的密码
    private int i = 60;
    private Timer timer;
    private MyTimerTask myTask;
    @Override
    public void setLayout() {
        setContentView(R.layout.act_regist);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("注册");


    }

    @OnClick({R.id.tv_code,R.id.tv_zc})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.tv_code://发送验证码
                phone=et_phone.getText().toString().trim();
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
                sendSms(phone, "true");
                break;


            case R.id.tv_zc://注册
                if(isValidate()){
//                    MyToastUtils.showShortToast(getApplicationContext(), "注册成功");
                   toRegister(phone, yzm, pwd);
                }

                break;

        }



    }

    /**
     * 是否可用
     *
     * @return
     */
    private boolean isValidate() {
        phone = et_phone.getText().toString().trim();
        yzm = et_yzm.getText().toString().trim();
        pwd = et_pwd.getText().toString().trim();
        cpwd=et_cpwd.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入手机号");
            et_phone.requestFocus();
            return false;
        }
        if(phone.length()!=11){
            MyToastUtils.showShortToast(getApplicationContext(), "请输入11位的手机号");
            et_phone.requestFocus();
            et_phone.setSelection(et_phone.length());
            return false;
        }
        if(!ZhetebaUtils.checkCellPhone(phone)){
            MyToastUtils.showShortToast(getApplicationContext(), "请输入正确的手机号码");
            return false;
        }

        if (TextUtils.isEmpty(yzm)) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入验证码");
            et_yzm.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入密码");
            et_pwd.requestFocus();
            return false;
        }
        if(pwd.length()<4){
            MyToastUtils.showShortToast(getApplicationContext(),"请输入至少4位密码");
            et_pwd.requestFocus();
            et_pwd.setSelection(pwd.length());
            return false;
        }
        if(pwd.length()>24){
            MyToastUtils.showShortToast(getApplicationContext(), "请输入不超过24位密码");
            et_pwd.requestFocus();
            et_pwd.setSelection(pwd.length());
            return false;
        }
        if(TextUtils.isEmpty(cpwd)){
            MyToastUtils.showShortToast(getApplicationContext(), "请确认密码");
            et_cpwd.requestFocus();
            return false;
        }

        if(!(cpwd.equals(pwd))){
            MyToastUtils.showShortToast(getApplicationContext(), "确认密码输入错误");
            et_cpwd.requestFocus();
            return false;
        }

        if(!ZhetebaUtils.checkPwd(pwd)){
            MyToastUtils.showShortToast(getApplicationContext(), "请输入6-20位字母或数字");
            return false;
        }
        if (!register_cb.isChecked()) {
            MyToastUtils.showShortToast(getApplicationContext(), "请勾选条例");
            return false;
        }

        return true;
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

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
               tv_code .setEnabled(true);
                tv_code.setText("重新发送");
                timer.cancel();
                myTask.cancel();
            } else {
                tv_code.setText(msg.what + "秒");
            }
        }

    };

    /**
     * 注册
     * @param username
     * @param captcha
     * @param password
     */
    public void toRegister(final String username,String captcha,String password){
        RequestManager.getUserManager().register(username, captcha, password, new ResultCallback<ResultBean<UserBean>>() {
            @Override
            public void onError(int status, String errorMsg) {

            }

            @Override
            public void onResponse(ResultBean<UserBean> response) {
                UserBean userBean=response.getData();
                UserInfoDao.saveUser(userBean.getUser());
                openActivity(RegInfoAct.class);
            }
        });

    }

    /**
     * 发送验证码接口
     * @param phoneNumber
     * @param isRegister
     */
    public void sendSms(String phoneNumber,String isRegister){
        RequestManager.getUserManager().sendSmsCaptcha(phoneNumber, isRegister, new ResultCallback<ResultBean<String>>() {
            @Override
            public void onError(int status, String errorMsg) {
                MyToastUtils.showShortToast(getApplicationContext(),errorMsg);
            }

            @Override
            public void onResponse(ResultBean<String> response) {
                i=60;
                tv_code.setEnabled(false);
                timer = new Timer();
                myTask = new MyTimerTask();
                timer.schedule(myTask, 0, 1000);
                MyToastUtils.showShortToast(getApplicationContext(),"验证码发送成功");

            }
        });


    }
}
