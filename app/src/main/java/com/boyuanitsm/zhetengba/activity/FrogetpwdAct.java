package com.boyuanitsm.zhetengba.activity;

import android.os.Bundle;

import com.boyuanitsm.zhetengba.R;
import com.boyuanitsm.zhetengba.base.BaseActivity;

/**
 * 忘记密码界面
 * Created by bitch-1 on 2016/4/28.
 */
public class FrogetpwdAct extends BaseActivity {
    @Override
    public void setLayout() {
        setContentView(R.layout.act_frogetpwd);

    }

    @Override
    public void init(Bundle savedInstanceState) {
        setTopTitle("忘记密码");
    }


}
