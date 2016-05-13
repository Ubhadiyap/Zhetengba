package com.boyuanitsm.zhetengba.activity.mine;

import android.os.Bundle;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;

/**
 * 注册界面
 * Created by bitch-1 on 2016/4/28.
 */
public class RegistAct extends BaseActivity {
    @Override
    public void setLayout() {
        setContentView(R.layout.act_regist);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("注册");

    }
}
