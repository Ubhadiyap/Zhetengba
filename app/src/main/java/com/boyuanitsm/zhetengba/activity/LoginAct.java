package com.boyuanitsm.zhetengba.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;
import com.boyuanitsm.zhetengba.utils.MyToastUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by bitch-1 on 2016/4/29.
 */
public class LoginAct extends BaseActivity {

    @ViewInject(R.id.etphone)
    private EditText etPhone;

    @ViewInject(R.id.etpwd)
    private EditText etPwd;

    private String phone,pwd;
    @Override
    public void setLayout() {
        setContentView(R.layout.login);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        TextView tv_regist = (TextView) findViewById(R.id.tv_regist);
        TextView tv_forget_pw = (TextView) findViewById(R.id.tv_forget_pw);
        tv_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(RegistAct.class);
            }
        });
        tv_forget_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(FrogetpwdAct.class);
            }
        });

  }

    @OnClick({R.id.tv_login})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.tv_login://登录
                if(isValidate()){
                    MyToastUtils.showShortToast(getApplicationContext(),"登录成功");
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
        phone = etPhone.getText().toString().trim();
        pwd = etPwd.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入手机号");
            etPhone.requestFocus();
            return false;
        }
        if(phone.length()!=11){
            MyToastUtils.showShortToast(getApplicationContext(), "请输入正确的手机号");
            etPhone.requestFocus();
            etPhone.setSelection(etPhone.length());
            return false;
        }
        if (TextUtils.isEmpty(pwd)) {
            MyToastUtils.showShortToast(getApplicationContext(), "请输入密码");
            etPwd.requestFocus();
            return false;
        }
        return true;
    }

}
