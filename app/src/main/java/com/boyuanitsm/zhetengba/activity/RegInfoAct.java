package com.boyuanitsm.zhetengba.activity;

import android.os.Bundle;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;

/**
 * 注册信息界面面
 * Created by bitch-1 on 2016/5/9.
 */
public class RegInfoAct extends BaseActivity {
    @Override
    public void setLayout() {
        setContentView(R.layout.act_reginfo);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("注册信息");

    }
}
