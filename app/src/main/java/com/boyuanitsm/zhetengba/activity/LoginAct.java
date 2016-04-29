package com.boyuanitsm.zhetengba.activity;

import android.os.Bundle;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;

/**
 * Created by bitch-1 on 2016/4/29.
 */
public class LoginAct extends BaseActivity {
    @Override
    public void setLayout() {
        setContentView(R.layout.login);

    }

    @Override
    public void init(Bundle savedInstanceState) {
       setTopTitle("登录");

    }
}
